# Mastering Maps in Kotlin and Java: HashMap, LinkedHashMap, TreeMap, Hashtable, SynchronizedMap, and ConcurrentHashMap

Working with key–value data structures is a daily routine for Java and Kotlin developers. At first glance, all `Map` types may look similar, but under the hood they differ significantly in **performance**, **ordering**, and most importantly, **thread safety**. Choosing the wrong one can lead to subtle **race conditions** or unnecessary **synchronization overhead**.

In this article, we’ll dive deep into:

1. **Core Map interfaces in Kotlin/Java**
2. **Default (non-synchronized) Map implementations**
3. **Synchronized and concurrent Map implementations**
4. **How collisions and race conditions are handled**
5. **Use cases: when to choose which Map**

---

## 1. Map Basics in Kotlin/Java

- **`Map<K,V>`** → read-only interface (Kotlin adds immutability guarantees).
- **`MutableMap<K,V>`** → allows modification.

These are just interfaces; the actual behavior comes from concrete implementations like `HashMap`, `LinkedHashMap`, etc.

---

## 2. Non-Synchronized Maps (Default)

These are **not thread-safe**. If multiple threads access them concurrently and at least one modifies the map, you must handle synchronization externally.

### **HashMap**

- Backed by an **array of buckets**. Each bucket stores entries with the same hash.
- Handles collisions with **linked lists** (Java 7) or **tree bins** (Java 8+) when collisions get too deep.
- **No synchronization.**
- **Order:** Unpredictable.

**Use case:**

The default choice for single-threaded or read-mostly scenarios. Extremely fast when concurrency is not a concern.

---

### **LinkedHashMap**

- Extends HashMap.
- Maintains insertion order (or access order if configured).
- Slight overhead compared to HashMap due to the linked list.

**Use case:**

When you need predictable iteration order, e.g. building caches, preserving query parameter order.

👉 **When to use LinkedHashMap**:

- If you need to **preserve insertion order** reliably.
- If you want to implement an **LRU cache** (Least Recently Used) by switching to access-order mode (`LinkedHashMap(…, true)`).
- Great when you want `HashMap`like speed but with predictable iteration.

---

### **TreeMap**

- Based on a **Red–Black tree**.
- Keys are kept in sorted order.
- No buckets — uses tree balancing.
- **No synchronization.**

**Use case:**

When sorted keys are critical, e.g. range queries, ordered navigation.

👉 **When to use TreeMap**:

- If you need the **keys always sorted** (by natural order or custom comparator).
- If you perform **range operations** (`headMap`, `tailMap`, `subMap`) or need nearest-key lookups (`ceilingEntry`, `floorEntry`).
- Acceptable to pay `O(log n)` cost per operation in exchange for ordering guarantees.

---

## 3. Synchronized Maps

Thread safety added on top of basic maps. But beware: synchronization often comes with a performance cost.

### **Hashtable (Legacy)**

- Old pre-Java 2 class.
- Fully synchronized → every operation acquires a global lock.
- Predictable but slow under contention.

**Use case:**

Almost none in modern code. Kept for backward compatibility.

---

### **Collections.synchronizedMap()**

- Wrapper that synchronizes access to an underlying map (e.g., `HashMap`).
- Uses a **single global lock** → both reads and writes block each other.
- Safe but can degrade performance badly when many threads access it.

**Use case:**

Quick fix if you need a thread-safe map and don’t want to refactor code. Suitable for light concurrency, not high throughput.

---

## 4. Concurrent Map: The Right Tool for Multi-Threading

### **ConcurrentHashMap**

- Designed for high concurrency.
- Still uses **buckets**, but handles thread safety differently:
    - **Reads** are usually **lock-free** (thanks to volatile semantics and memory fencing).
    - **Writes** use **fine-grained locking (lock striping)** or **CAS (Compare-And-Swap)** on individual buckets.
- Provides atomic methods (`putIfAbsent`, `computeIfAbsent`, `merge`) that avoid race conditions without external locking.
- Avoids the global lock overhead of `Hashtable` and `synchronizedMap`.

**Use case:**

The go-to choice when multiple threads frequently read and update a shared map. Perfect for caches, registries, counters, or state tracking in concurrent systems.

---

## 5. Buckets vs. Race Conditions

- **Buckets** are purely a **collision resolution mechanism**.
    
    They decide *where* entries with the same hash code go.
    
    → They do **not** make a map thread-safe.
    
- **Race conditions** happen when multiple threads update the map simultaneously.
    
    Only **synchronization mechanisms** (locks or CAS) can prevent inconsistent state.
    
    → That’s why `ConcurrentHashMap` exists.
    

---

## 6. Choosing the Right Map

Here’s a quick decision guide:

| Situation | Recommended Map | Why |
| --- | --- | --- |
| Single-threaded, general use | `HashMap` | Fastest, minimal overhead |
| Need predictable order | `LinkedHashMap` | Preserves insertion/access order |
| Need sorted keys | `TreeMap` | Sorted by comparator or natural order |
| Light concurrency, quick fix | `Collections.synchronizedMap` | Easy wrapper but global lock overhead |
| Heavy concurrency, frequent updates | `ConcurrentHashMap` | Fine-grained locking + atomic ops |
| Legacy code (pre-Java 2) | `Hashtable` | Only for backward compatibility |

---

## 7. Final Thoughts

- Use **HashMap** by default unless you specifically need ordering or sorting.
- Use **LinkedHashMap** when you want predictable iteration order or LRU-like behavior.
- Use **TreeMap** when sorting and range queries are essential.
- Avoid `Hashtable` unless maintaining old APIs.
- `Collections.synchronizedMap` is a band-aid, not a long-term solution.
- For **real concurrency**, always prefer **ConcurrentHashMap** — it avoids global lock bottlenecks while still ensuring safe multi-threaded access.

**Rule of thumb:**

👉 If concurrency matters → go straight to **ConcurrentHashMap**.

👉 If not → stick with **HashMap** or its ordered variants for simplicity and speed.