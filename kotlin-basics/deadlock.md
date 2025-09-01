# What is a deadlock?

Two (or more) execution units wait on each other’s locks/resources and **none can make progress**. On Android this often shows up as **UI freeze/ANR** or a background thread pegged at 0% CPU doing nothing.

---

# The 5 most common deadlocks on Android

1. **Lock order inversion**

```kotlin
val A = Any()
val B = Any()

// Thread 1
synchronized(A) { synchronized(B) { /* ... */ } }

// Thread 2
synchronized(B) { synchronized(A) { /* ... */ } } // ← can deadlock

```

**Fix**

- Always acquire locks in **one global order** (e.g., A → B everywhere).
- Or collapse to **one lock**, or use **`ReentrantLock.tryLock`** with timeouts.
- Prefer **coroutine `Mutex`** over `synchronized`.

---

1. **Blocking the main thread (ANR-style deadlock)**

```kotlin
// ❌ Never do this on the main thread
runBlocking(Dispatchers.Main) { someBackgroundJob.join() } // UI waits; BG may wait on UI

```

**Fix**

- Don’t block the main thread. Use `lifecycleScope.launch { ... }` + `await()`/`suspend`.
- Keep I/O on `Dispatchers.IO`. Use `withContext(Dispatchers.IO)` around blocking calls.

---

1. **Main ↔ background cross-dependency**
- BG thread holds a lock and posts to main; main thread **synchronously** calls back into BG and needs the same lock.
    
    **Fix**
    
- Make UI calls **asynchronous** (post/launch), and **release locks** before posting to main.
- Avoid calling into UI while holding a lock.

---

1. **Coroutine Mutex misuse (re-entrancy, forgotten release)**

```kotlin
val m = Mutex()

suspend fun foo() = m.withLock {
  // calls bar() which tries to lock m again → deadlock if same coroutine path
  bar()
}
```

**Fix**

- Avoid re-entrant locking with the same `Mutex`.
- Keep critical sections **tiny**; never call out to code that might lock again.
- Consider **immutable data + message passing (Channel/actor)**.

---

1. **Waiting on a result that waits on you (futures/promises)**
- A flow collector awaits a value that will only be emitted **after** the collector completes.
    
    **Fix**
    
- Break cycles: emit on separate scope/dispatcher; use **buffer()** on flows to decouple.

---

# Safer primitives & patterns

### Prefer coroutines over raw threads

- Use **structured concurrency** (`lifecycleScope`, `viewModelScope`).
- Use **`withTimeout`/`withTimeoutOrNull`** for potentially blocking ops.

### Use `Mutex`/`Semaphore` from `kotlinx.coroutines`

```kotlin
val mutex = Mutex()

suspend fun updateState(block: () -> Unit) {
  withTimeout(2_000) { mutex.withLock { block() } } // time-bounded critical section
}

```

### If you must use Java locks

```kotlin
val lock = ReentrantLock()
if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
  try { /* critical */ } finally { lock.unlock() }
} else {
  // fallback / log contention
}

```

### Avoid nested locks

- Prefer **single writer** model (one dispatcher/actor owns the state).
- Or **lock ordering** + **tiny critical sections**.

### Don’t block coroutines

- Avoid `runBlocking` on Android.
- Avoid `Job.join()`/`Deferred.await()` on main unless you launched it **off** the main dispatcher.

---

# Detecting & debugging deadlocks

### 1) Take a thread dump (shows BLOCKED/RUNNABLE and owners)

```bash
adb shell ps | grep your.package
adb shell kill -3 <pid>      # writes stack traces to logcat

```

Look for:

- `BLOCKED` threads and **“waiting to lock <…> owned by …”** lines.
- Main thread stuck inside `synchronized`/`wait()`/`Future.get()`/`join()`.

### 2) Coroutines debug tools

- Add `kotlinx-coroutines-debug` in debug builds.

```kotlin
DebugProbes.install()
DebugProbes.dumpCoroutines() // call from a debug action or breakpoint

```

You’ll see suspended stacks and who’s waiting on which `Mutex`.

### 3) StrictMode / ANR analysis

- Enable **StrictMode** in debug to catch main-thread disk/network.
- Inspect ANR traces (`data/anr/traces.txt`) for main thread blocking patterns.

### 4) Profilers & log markers

- Android Studio **Profiler** → threads timeline; mark entry/exit of critical sections with logs to spot “entered but never exited”.

---

# Concrete recipes

### A) Replace nested `synchronized` with a single `Mutex`

```kotlin
class Repository {
  private val mu = Mutex()
  private var state: Map<String,Any> = emptyMap()

  suspend fun read(): Map<String,Any> = mu.withLock { state }
  suspend fun write(update: Map<String,Any>) = mu.withLock { state = update }
}

```

### B) Actor model for shared state (no explicit locks)

```kotlin
sealed interface Msg
data class Put(val k:String, val v:Int): Msg
data class Get(val k:String, val reply: CompletableDeferred<Int?>): Msg

val actor = CoroutineScope(Dispatchers.Default).actor<Msg> {
  val map = HashMap<String, Int>()
  for (msg in channel) when (msg) {
    is Put -> map[msg.k] = msg.v
    is Get -> msg.reply.complete(map[msg.k])
  }
}

```

### C) Timeout & fallback instead of indefinite blocking

```kotlin
val result = withTimeoutOrNull(1500) { expensiveCall() } ?: defaultValue

```

### D) For caches: **ConcurrentHashMap** + atomic ops (avoid external locks)

```kotlin
val cache = ConcurrentHashMap<String, Data>()
fun getOrLoad(key: String): Data =
  cache.computeIfAbsent(key) { loadFromNetworkBlocking(key) } // do on IO dispatcher!

```

> Note: computeIfAbsent may execute the mapping function under internal locks; keep it short and move blocking I/O off to Dispatchers.IO.
> 

---

# Preventive checklist

- [ ]  Never block the **main thread** with `runBlocking`, `join`, `Future.get`, or disk/network I/O.
- [ ]  Keep critical sections **tiny**; do not call into UI or external services while holding locks.
- [ ]  Enforce **global lock ordering**; better yet, **one lock**.
- [ ]  Prefer **immutable data + message passing** over shared mutable state.
- [ ]  Use **`Mutex`/`Semaphore`** or **ConcurrentHashMap** atomic ops instead of nested `synchronized`.
- [ ]  Add **timeouts** around anything that could stall.
- [ ]  Turn on **StrictMode** in debug and learn to read **thread/coroutine dumps**.