# Functional Programming (FP) — Complete Guide

---

**Functional Programming (FP)** is a paradigm where programs are built using **pure functions** and **immutable data**.

Instead of telling the computer *how* to do things step by step (imperative style), FP describes *what transformations* should happen to the data.

FP emphasizes:

- **Pure functions** (no hidden side effects).
- **Immutability** (data never changes, only new versions are created).
- **Composition** (build larger logic from small functions).
- **Side-effect isolation** (I/O, network, DB pushed to the edges).

Benefits:

- Readability
- Maintainability
- Testability

---

## FP vs OOP (Comparison)

| Aspect | FP (Functional Programming) | OOP (Object-Oriented Programming) |
| --- | --- | --- |
| **Core unit** | Function (transformations of data) | Object (state + behavior together) |
| **Data** | Immutable, explicit values | Mutable fields, encapsulated in objects |
| **State** | Modeled as values (`Result`, `Option`, `sealed`) | Hidden inside objects, changed by methods |
| **Errors** | Values (`Either`, `Result`) | Exceptions thrown/caught at runtime |
| **Composition** | Function composition, pipelines | Inheritance, interfaces, polymorphism |
| **Testing** | Easy: pure functions test in isolation | Often needs mocks/stubs due to hidden state |
| **Concurrency** | Safe (no mutation, no race conditions) | Requires locks, careful synchronization |
| **Style** | Declarative (“what” to compute) | Imperative (“how” to compute step by step) |

## **Rule of thumb:**

- Use **OOP** when modeling long-lived `entities` with identity (e.g., Android framework classes).
- Use **FP** when writing **multiple state, update UI, concurrency, testing, easy logic,** **business logic, reducers, transformations, pipelines**.
- In practice, modern Kotlin/Android apps mix both.

## 1. Core Mental Model

- **Pure functions** → same input, same output, no hidden state or side effects.
- **Side effects** = any observable effect of a function **outside of returning a value**.
    - Examples include: printing to console, modifying a global variable, writing to a database, logging, or calling an API.
    - Functions with side effects are **impure**, since their output depends not only on inputs but also on external state.
- **Impure functions = functions + side effects**
- **Immutable data** → values never mutate; new versions created instead (**`copy` in Kotlin**).
- **Referential transparency** → any expression can be replaced with its value safely.
- **Expressions > statements** → pipelines of transformations, not step-by-step mutation.
- **Errors & state as values** → e.g. `Option`, `Result`, `Either` instead of exceptions/mutable vars.
- **Side effects at the edges** → isolate I/O, DB, network in small wrappers; keep domain pure.
- **First class functions = first-class citizens**
    - Stored in variables.
    - Passed as arguments to other functions.
    - Returned as values from functions.
- **High order functions** = any function that:
    - Takes another function as a parameter, or
    - Returns a function as a result.
- **Function composition =** building new functions by combining existing ones
- **Idempotent**
    - A function (or operation) is **idempotent** if calling it multiple times with the same input produces the **same result** as calling it once.
    - Repeating the call doesn’t change the outcome beyond the first.
    - Idempotent can have side effects but multiple calls will have the same effects at one call
    - Example: abs(x), API, ….
- **Inline function**
    - An **inline function** is one where the compiler substitutes the function’s body directly at the call site.

## 2. Signs You’re Doing FP

✅ You use `map/filter/fold` more than `for` loops.

✅ You represent missing/error states with `Option/Result`.

✅ Your functions have no side effects (no logging, DB, I/O inside domain).

✅ You compose small functions into bigger pipelines.

✅ You can test 90% of your logic with zero mocks.

✅ Your domain model uses **sealed classes + data classes** instead of enums/flags.

✅ You can inline expressions and the code still “means the same thing”.

If most of these are true → you’re doing FP.

---

## 3. Use Cases Where FP Shines

- **Data transformation pipelines**: parsing JSON → mapping DTOs → domain objects.
- **Validation flows**: chaining multiple checks (`flatMap` with `Either`).
- **State management**: reducers (MVI, Redux-style) — states = values, actions = transformations.
- **Error handling**: replacing exceptions with safe, typed flows of `Result`/`Either`.
- **Concurrency**: safe parallel processing (no race conditions thanks to immutability).
- **Testing**: pure domain logic is trivial to test.
- **UI rendering**: Compose/React are essentially FP for UI — UI = function(state).

---

## 4. Trade-offs

- **Performance**: immutability/recursion can add overhead; use persistent collections.
- **Learning curve**: shifting from imperative “step-by-step” to declarative “transformations”.
- **Interfacing reality**: side effects (I/O, DB) must be carefully isolated.
- **Debuggability**: stack traces less linear; need to think in data flow.

---

## 5. Core FP Constructs

### 5.1 Higher-order functions

```kotlin
listOf(5, 12, 3, 20)
    .filter { it >= 10 }      // keep
    .map { (it * 0.9).toInt() } // discount
    .fold(0) { acc, x -> acc + x } // sum

```

### 5.2 Option (Maybe)

```kotlin
sealed interface Option<out A> {
    data class Some<out A>(val value: A) : Option<A>
    data object None : Option<Nothing>
}
fun <A> A?.toOption() = if (this == null) Option.None else Option.Some(this)

```

### 5.3 Either (Result)

```kotlin
sealed interface Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>
    data class Right<out R>(val value: R) : Either<Nothing, R>
}

fun parseInt(s: String): Either<String, Int> =
    s.toIntOrNull()?.let { Either.Right(it) } ?: Either.Left("NaN: $s")
```

### 5.4 Function composition

```kotlin
infix fun <A,B,C> ((B)->C).compose(g: (A)->B): (A)->C = { a -> this(g(a)) }

val double = { x: Int -> x*2 }
val inc = { x: Int -> x+1 }
val pipeline = double compose inc
println(pipeline(3)) // (3+1)*2 = 8
```

### 5.5 Immutability & reducers

```kotlin
data class Cart(val items: List<String>)

sealed interface Action { data class Add(val item: String): Action; object Clear: Action }
fun reduce(state: Cart, action: Action): Cart = when(action) {
    is Action.Add -> state.copy(items = state.items + action.item)
    Action.Clear -> Cart(emptyList())
}
```

---

## 6. Laziness & Infinite Data

```kotlin
val squares = generateSequence(1) { it + 1 }
    .map { it * it }
    .take(5)
    .toList() // [1,4,9,16,25]
```

---

## 7. Errors & State as Values

### 7.1 Safe division

```kotlin
fun safeDivide(a: Int, b: Int): Either<String, Int> =
    if (b == 0) Either.Left("Divide by zero") else Either.Right(a / b)
```

### 7.2 UI State with sealed types

```kotlin
sealed interface ViewState {
    object Loading : ViewState
    data class Error(val msg: String): ViewState
    data class Ready(val data: String): ViewState
}
```

---

## 8. Effects at the Edges (Android)

- **Repository** = does I/O, wraps results into `Either`.
- **ViewModel** = maps `Either` into `ViewState`.
- **Domain** = pure transformations.

```kotlin
fun interface HttpGet { suspend fun invoke(url: String): Either<String,String> }

class Repo(private val http: HttpGet) {
    suspend fun load(): Either<String, Cart> =
        http("https://api/cart").flatMap { json -> decode(json) }
    private fun decode(json: String) = Either.Right(Cart(emptyList()))
}
```

---

## 9. Concurrency in FP

```kotlin
suspend fun <A,B> Iterable<A>.pmap(f: suspend (A)->B): List<B> =
    coroutineScope { map { async { f(it) } }.awaitAll() }
```

---

## 10. Testing in FP

- **Unit tests**: trivial, since pure functions have no dependencies.
- **Property-based tests**: check general laws, not just examples.

```kotlin
checkAll<Int,Int> { a, b -> gcd(a,b) == gcd(b,a) }
```

---

## 11. Advanced FP Building Blocks

### 1) Functor — “map in a context”

**Idea:** If you can apply a pure function `A -> B` to a value **inside a context** `F<A>` and get `F<B>`, you have a Functor.

**Type (pseudo-sig):** `map: (F<A>, (A)->B) -> F<B>`

**Laws (must hold):**

- **Identity:** `fa.map(::identity) == fa`
- **Composition:** `fa.map(f compose g) == fa.map(g).map(f)`

**Kotlin examples**

```kotlin
// Option-like
sealed interface Option<out A> {
  data class Some<out A>(val value: A) : Option<A>
  data object None : Option<Nothing>
}
inline fun <A,B> Option<A>.map(f: (A)->B): Option<B> =
  when (this) { is Option.Some -> Option.Some(f(value)); Option.None -> Option.None }

// Either (Right-biased)
sealed interface Either<out L, out R> {
  data class Left<out L>(val value: L) : Either<L, Nothing>
  data class Right<out R>(val value: R) : Either<Nothing, R>
}
inline fun <L,R,B> Either<L,R>.map(f: (R)->B): Either<L,B> =
  when (this) { is Either.Left -> this; is Either.Right -> Either.Right(f(value)) }

```

**When to use:** Transforming successful values without changing the surrounding effect/context (list, option, async, result).

---

### 2) Applicative — “combine independent contexts”

**Idea:** Lift **pure values** into a context and apply **functions in a context** to **values in a context**. Useful when **steps don’t depend** on each other (can accumulate errors).

**Types (pseudo-sigs):**

- `pure: A -> F<A>` (a.k.a. `just`, `unit`)
- `ap: F<(A)->B> -> F<A> -> F<B>` (apply a function wrapped by context)

**Core laws:**

- **Identity, Homomorphism, Interchange, Composition** (guarantee consistent application order/shape)

**Kotlin, simple Option applicative**

```kotlin
fun <A> optionPure(a: A): Option<A> = Option.Some(a)

inline fun <A,B> Option<(A)->B>.ap(oa: Option<A>): Option<B> =
  when (this) {
    is Option.Some -> oa.map(this.value)
    Option.None    -> Option.None
  }

// Build a User from independent validated fields (Option = present/missing)
data class User(val name: String, val age: Int)

fun mkUser(name: String): (Int) -> User = { age -> User(name, age) }

val oName: Option<String> = Option.Some("Tung")
val oAge: Option<Int> = Option.Some(21)

val user: Option<User> =
  optionPure(::mkUser)         // F<(String)->(Int)->User>
    .ap(oName.map(::mkUser))   // or use a curried constructor
// simpler, with a helper:
fun <A,B,C> liftA2(f: (A,B)->C, fa: Option<A>, fb: Option<B>): Option<C> =
  when (fa) {
    is Option.Some -> fb.map { b -> f(fa.value, b) }
    Option.None    -> Option.None
  }
val user2 = liftA2(::User, oName, oAge)

```

**Why Applicative > Monad in validation:** Applicative can **accumulate** multiple independent errors (see `Validated`) instead of short-circuiting on the first failure.

**Validated (accumulating errors) sketch**

```kotlin
data class Validated<E, A>(val errors: List<E>?, val value: A?)
fun <E,A> ok(a: A) = Validated<E,A>(null, a)
fun <E,A> err(e: E) = Validated<E,A>(listOf(e), null)

fun <E,A,B> Validated<E, (A)->B>.ap(v: Validated<E,A>): Validated<E,B> =
  when {
    value != null && v.value != null -> ok(value.invoke(v.value))
    else -> Validated((errors.orEmpty() + v.errors.orEmpty()).ifEmpty { null }, null)
}

```

---

### 3) Monad — “sequence dependent steps”

**Idea:** If the **next computation depends on the previous result**, you need `flatMap`. Monads model *sequencing*.

**Types (pseudo-sigs):**

- `pure: A -> M<A>`
- `flatMap: M<A> -> (A -> M<B>) -> M<B>` (aka `bind`)
- (Derived) `map(f) = flatMap { a -> pure(f(a)) }`

**Laws:**

- **Left identity:** `pure(a).flatMap(f) == f(a)`
- **Right identity:** `m.flatMap(::pure) == m`
- **Associativity:** `(m.flatMap(f)).flatMap(g) == m.flatMap { a -> f(a).flatMap(g) }`

**Kotlin Either monad**

```kotlin
inline fun <L,R,B> Either<L,R>.flatMap(f: (R)->Either<L,B>): Either<L,B> =
  when (this) { is Either.Left -> this; is Either.Right -> f(value) }

// Example: parse → divide, where step2 uses step1’s value
fun parseInt(s: String): Either<String, Int> =
  s.toIntOrNull()?.let { Either.Right(it) } ?: Either.Left("NaN: $s")

fun safeDiv(a: Int, b: Int): Either<String, Int> =
  if (b == 0) Either.Left("Div0") else Either.Right(a / b)

val res: Either<String, Int> =
  parseInt("40").flatMap { a ->
    parseInt("2").flatMap { b ->
      safeDiv(a, b)
    }
  }

```

**Kleisli composition (compose effectful functions):**

```kotlin
fun <A,B,C> ((A)->Either<String,B>).andThen(g: (B)->Either<String,C>): (A)->Either<String,C> =
  { a -> this(a).flatMap(g) }

```

**When to use:** Pipelines where each step’s **shape/choice depends** on prior results (e.g., auth → fetch → decode → business rules).

---

### 4) Monoid — “combine” with an identity

**Idea:** A type with an **associative** binary operation and an **identity element**. Powers `fold` and log/aggregation.

**Typeclass:**

```kotlin
interface Monoid<A> {
  val empty: A
  fun combine(x: A, y: A): A
}
object SumInt : Monoid<Int> {
  override val empty = 0
  override fun combine(x: Int, y: Int) = x + y
}
object AllBoolean : Monoid<Boolean> {
  override val empty = true
  override fun combine(x: Boolean, y: Boolean) = x && y
}

```

**Laws:**

- **Associativity:** `combine(a, combine(b,c)) == combine(combine(a,b), c)`
- **Identity:** `combine(a, empty) == a == combine(empty, a)`

**Fold with a monoid**

```kotlin
fun <A> List<A>.fold(m: Monoid<A>): A = this.fold(m.empty, m::combine)
// Examples
listOf(1,2,3).fold(SumInt)          // 6
listOf(true, true, false).fold(AllBoolean) // false

```

**Why it matters:** Once you define a monoid, you get efficient, structure-agnostic combining (parallelizable reductions).

---

### 5) State / Reader / Writer — modeling effects as pure values

### 5.1 State — threading state without mutation

**Idea:** `State<S, A>` is a pure function `S -> (A, S)` — given input state, produce a value and a new state. Great for counters, RNGs, reducers.

```kotlin
data class State<S, out A>(val run: (S) -> Pair<A, S>) {
  fun <B> map(f: (A)->B) = State<S,B> { s ->
    val (a, s2) = run(s); f(a) to s2
  }
  fun <B> flatMap(f: (A)->State<S,B>) = State<S,B> { s ->
    val (a, s2) = run(s); f(a).run(s2)
  }
}
// Helpers
fun <S> get() = State<S, S> { s -> s to s }
fun <S> put(ns: S) = State<S, Unit> { _ -> Unit to ns }
fun <S> modify(f: (S)->S) = get<S>().flatMap { s -> put(f(s)) }

// Example: generate two fresh IDs using a counter as state
val freshId: State<Int, Int> = State { s -> s to (s + 1) }
val twoIds: State<Int, Pair<Int,Int>> =
  freshId.flatMap { a -> freshId.map { b -> a to b } }

val (ids, finalState) = twoIds.run(100) // ids=(100,101), finalState=102

```

**Android fit:** Pure reducers for complex UIs, deterministic simulations, pagination cursors, etc.

---

### 5.2 Reader — dependency injection as a value

**Idea:** `Reader<Env, A>` is `Env -> A`. You “ask” for the environment (config/ports), compose computations, then **run** once with a concrete Env. Perfect for DI without global singletons.

```kotlin
data class Reader<R, out A>(val run: (R) -> A) {
  fun <B> map(f: (A)->B) = Reader<R,B> { r -> f(run(r)) }
  fun <B> flatMap(f: (A)->Reader<R,B>) = Reader<R,B> { r -> f(run(r)).run(r) }
}
fun <R> ask() = Reader<R, R> { it }
fun <R,A> local(f: (R)->R, ra: Reader<R,A>) = Reader<R,A> { r -> ra.run(f(r)) }

// Example: build a URL from Env, then request builder
data class Env(val baseUrl: String, val token: String)

val authHeader: Reader<Env, Map<String,String>> =
  ask<Env>().map { env -> mapOf("Authorization" to "Bearer ${env.token}") }

val cartUrl: Reader<Env, String> =
  ask<Env>().map { env -> "${env.baseUrl}/cart" }

val runWith: Env = Env("https://api.example.com", "XYZ")
val url = cartUrl.run(runWith)     // "https://api.example.com/cart"
val hdr = authHeader.run(runWith)  // {"Authorization":"Bearer XYZ"}

```

**Android fit:** Compose functions that need config/ports (Clock, Http, DB) *without* passing them manually everywhere or using globals.

---

### 5.3 Writer — accumulate logs (requires a Monoid)

**Idea:** `Writer<W, A>` pairs a value with a **log** of type `W`. If `W` is a Monoid (e.g., `List<String>`, `String`, `Int` with `+`), logs can accumulate automatically.

```kotlin
data class Writer<W, out A>(val value: A, val log: W)

fun <W: Any, A, B> Writer<W, A>.map(monoid: Monoid<W>, f: (A)->B): Writer<W,B> =
  Writer(f(value), log)

fun <W: Any, A, B> Writer<W, A>.flatMap(monoid: Monoid<W>, f: (A)->Writer<W,B>): Writer<W,B> {
  val (b, w2) = f(value)
  return Writer(b, monoid.combine(this.log, w2))
}

object LogList : Monoid<List<String>> {
  override val empty = emptyList<String>()
  override fun combine(x: List<String>, y: List<String>) = x + y
}

// Example: compute with breadcrumbs
fun step1(n: Int) = Writer(listOf("step1:$n"), n + 1)
fun step2(n: Int) = Writer(listOf("step2:$n"), n * 2)

val result: Writer<List<String>, Int> =
  step1(3).flatMap(LogList) { a -> step2(a) }
// result.value = 8, result.log = ["step1:3", "step2:4"]

```

**Android fit:** Pure audit trails, debug traces, metrics aggregation, domain-level explanations for UI.

# Choosing the right tool (quick mental model)

- **Just transform inside a box?** → **Functor (`map`)**
- **Combine multiple independent boxes?** → **Applicative (`pure` + `ap` / `liftA2`)**
- **Next step depends on previous result?** → **Monad (`flatMap`)**
- **Need to combine many results/logs?** → **Monoid (`combine`/`empty`)**
- **Thread state without mutation?** → **State**
- **Pass read-only dependencies cleanly?** → **Reader**
- **Collect logs/explanations as you go?** → **Writer**

# Where this pays off in Android/Kotlin

- **Reducers** (MVI/Compose): use **State** + **Monoid** to fold actions.
- **Validation** (sign-up forms): use **Applicative** to **accumulate** field errors.
- **Networking pipelines**: use **Monad** (`Either`) to sequence parse/validate/transform.
- **Configurable services**: use **Reader** to inject base URLs, auth, clocks, UUIDs.
- **Explainable UI**: use **Writer** to surface “how we computed this” to the screen/logs.

If you want, I can bundle these snippets into a tiny **`fp-core` Kotlin module** (ready to import) with tests demonstrating the **laws** via property-based testing.

---

## 12. Java 17 FP Equivalents

```java
record CartItem(String name, int price) {}

List<CartItem> items = List.of(new CartItem("apple",10));
int total = items.stream().mapToInt(CartItem::price).sum();

Optional<Integer> parsed = tryParse("123"); // avoid exceptions

```

For richer FP in Java → libraries like **Vavr**.

---

## 13. When NOT to Force FP

- **Low-level perf hotspots**: sometimes mutation is faster (graphics, DSP).
- **Interop APIs**: Android framework itself is OOP/mutable; adapters may need mutability.
- **Teams unfamiliar with FP**: introducing gradually is better.

---

## 14. Adoption Roadmap

1. Start with **immutable data + pure functions**.
2. Replace exceptions with **Result/Either**.
3. Use **map/filter/fold** consistently instead of loops.
4. Model domain states with **sealed classes**.
5. Push I/O to the edge (repos, adapters).
6. Introduce **property-based testing**.
7. Only later, explore advanced patterns (monads, applicatives).

---

## 15. Quick Self-Check

- Are functions pure?
- Is state immutable?
- Are errors values, not exceptions?
- Are side effects pushed outward?
- Does the code read like a **dataflow** rather than a **script**?

If yes → you’re practicing FP.

---

## TL;DR

Functional Programming is about:

**modeling computation as transformations of immutable data with pure functions, making errors/states explicit as values, and pushing side effects to the boundaries.**

The result: safer, more predictable, composable, and testable systems — perfect fit for Android with Kotlin + coroutines + Compose.