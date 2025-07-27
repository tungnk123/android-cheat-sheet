# 📌 Reactive Programming: Simplify Asynchronous Development for Android & iOS

## **📌 Introduction**

### **Overview**

- **What is Reactive Programming?**
    - **Declarative** programming paradigm focusing on **data streams** and the **propagation of changes**.
    - Handles **asynchronous** data streams gracefully.
    - Instead of manually managing state and handling callbacks, reactive programming lets you define relationships between **data sources** and **automatically** **update outcomes** when inputs change.
    - **Imperative**: You manually handle data changes, state, and errors.
    - **Declarative (Reactive)**: You **subscribe** to data and **define how to react**, and the system manages the flow, timing, and errors for you.
- **Why is it relevant?**
    - **Improved Responsiveness**
        - UI can instantly reflect changes in underlying data (e.g., live chat, stock updates, search suggestions).
    - **Simplifies Asynchronous Code**
        - Makes code cleaner by replacing nested callbacks with declarative stream chains.
    - **Better Error Handling**
        - Provides structured error recovery (e.g., retry, fallback) within streams.
    - **Easier State Management**
        - Automatically updates dependent components when the data changes—less boilerplate code

### **Session Goals**

- Understand core reactive concepts.
- Compare tools available for Android (RxJava, Kotlin Flow) and iOS (RxSwift, Combine).
- Demonstrate practical use cases and best practices.

---

## **🚀 Core Concepts**

### **Key Terms**

- **Data streams** = a flow of data objects that start somewhere and consumed elsewhere
    - Streams can emit **3** different things: a value (of some type), an error, or a "completed" signal
    - 
    
    [](https://tfj24htc35b.sg.larksuite.com/space/api/box/stream/download/asynccode/?code=MmQxMzA1ZDBiNmY4NWQ5M2QxMTMyYjAxODYyN2NhZDVfT1pxWVUwbTJnYWd2NzRnaEk2Rzc1RHBNZ2MzMUtUYzlfVG9rZW46VFlmYWJmWG5Vb1BFdEN4TFA4WGxBSFRTZ3ZiXzE3NTM1ODE4Nzg6MTc1MzU4NTQ3OF9WNA)
    

Figure 1: Illustration of what is a stream, subscription, and unsubscription

- **Observable / Publisher:** A source that emits a stream of data over time.
    - You can observe, enabling you to listen for and react to incoming data
    - Observables are characterized by the following **three** aspects:
        - **Data Lifecycle**: An observable is a primitive type that can contain zero or multiple values. These values are pushed over any duration, determining the lifecycle of the stream.
        - **Cancellable**: Observables can be cancelled at any time. By informing the producer that you no longer require updates, you can cancel a subscription to an observable.
        - **Lazy Evaluation**: Observables are lazy, meaning that they do not perform any actions until you subscribe to them.
    - Example:
        - RxJava & RxSwift: `Observable`
        - Combine: `Publisher`
        - Kotlin Flow: `Flow`
- **Observer / Subscriber :** Reacts to the data emitted by an Observable/Publisher. It defines what to do when:
    - Data is received
    - An error occurs
    - The stream completes
- **Subject** = A hybrid type that’s both an **Observer** (it can subscribe to upstream sources) and an **Observable** (it multicasts received events to its own subscribers).
    - **Observer side**: you can call `onNext`/`onError`/`onComplete` (or simply subscribe it to another stream) so it “listens.”
    - **Observable side**: any number of downstream subscribers will all get the same events.
    - **Hot** by nature: it emits values as they arrive, regardless of whether anyone is currently subscribed (unless you buffer/replay).
    - **Examples**:
        - **RxJava**: `PublishSubject`, `BehaviorSubject`, `ReplaySubject`, `AsyncSubject`
        - **Combine**: `PassthroughSubject`, `CurrentValueSubject`
        - **Kotlin Flow**: `SharedFlow`, `StateFlow`

---

### Additional terms

- **Disposable / Cancellable**
    - Used to **manage and stop** subscriptions when no longer needed:
        - Prevents **memory leaks**
        - Cleans up system resources
    - Example:
        
        
        | **Platform** | **Term** |
        | --- | --- |
        | RxJava | Disposable |
        | Combine | AnyCancellable |
        | Kotlin Flow | Job or managed via coroutine scope (collect {}) |
- **Operators**
    - **Operators** are functions used to transform, filter, and combine data within a stream. They allow you to build **declarative, composable pipelines**.
    - Common Examples:
        - `map`: Transform data
        - `filter`: Remove unwanted data
        - `debounce`: Ignore rapid emissions (e.g. from user input)
        - `combineLatest`: Merge multiple streams into one
        - `buffer`: Collects emissions into a list, based on count or time window.
        - `distinctUntilChanged`: Emits only when the current item is different from the previous.
        - `throttle(duration)`: Emits the first item during each specified time window.
        - `merge / combine`: Combines multiple streams by interleaving their emissions.
        - `catch`: Catches an error and handles it or emits fallback.
        - `retry(n)`: Retries the stream up to `n` times if an error occurs.
- **Schedulers (In RxJava) -> like Dispatcher in Coroutine or DispatchQueue in Swift**
    - **Schedulers** control what thread a piece of work runs on — crucial for handling concurrency and performance.
    - Common Types in RxJava:
        - `IO`: For disk/network I/O operations
        - `Computation`: For CPU-intensive tasks (e.g., calculations)
        - `MainThread`: For UI updates (Android)

## **🚀 How it works**

1. **Thread-per-Request Model**
- **Definition**: Each incoming client request is handled by its own OS thread
- **Characteristics**
    - **Blocking**: thread waits on I/O or processing
    - **High memory**: ~1 MB stack per thread
    - **Context switches** become expensive at scale
    - **No backpressure**: can overwhelm resources

---

1. **Event Loop Model**
- **Definition**: A single thread runs a loop that dispatches ready events
- **How It Works**
    - OS signals I/O completion → enqueues callback
    - Event loop dequeues and runs each callback on the same thread
    - Long-running/blocking I/O is offloaded to the kernel
- **Pros/Cons**
    - ✓ Minimal context-switch overhead
    - ✗ Callback nesting (“callback hell”)
    - ✗ Manual flow-control

---

1. **OS Thread**
- **Definition**: Native thread managed by the operating system (pthreads, NSThread)
- **Key Points**
    - Own stack and registers
    - Scheduled by the kernel with timeslices
    - Expensive to create/destroy

---

1. **Thread Pool**
- **Definition**: A reusable pool of worker threads waiting for tasks
- **Mechanism**
    - Submit tasks to a shared queue
    - Idle thread picks up and executes each task
- **Benefits**
    - Reuses threads → reduces creation overhead
    - Caps maximum concurrent threads → controllable resource use

---

1. **Executor**
- **Definition**: An abstraction (Java/Kotlin) for submitting and managing tasks
- **Examples**
    - `ThreadPoolExecutor`, `ScheduledThreadPoolExecutor`
- **Responsibilities**
    - Thread allocation policy (max threads, queue type, keep-alive)
    - Task submission APIs (`execute()`, `submit()`, `schedule()`)

---

1. **Reactive Programming**
- **Concept**: Declarative streams of events + operators + schedulers
- **Components**
    - **Producers** (Observable / Flow / Publisher) emit data
    - **Operators** (`map`, `filter`, `flatMap`, `buffer`, etc.) transform streams
    - **Schedulers/Dispatchers** determine execution context
- **Under the Hood**
    - **No new thread per event**: each event is wrapped as a task and submitted to an existing thread pool or event queue
        - JVM (RxJava): uses `Schedulers.io()`, `computation()` backed by `ThreadPoolExecutor`
        - Kotlin Flow: uses `Dispatchers.IO`, `Default`, `Main` backed by coroutine dispatchers
        - iOS (Combine/RxSwift): uses GCD `DispatchQueue` or `OperationQueue`
    - **Backpressure** built in (`buffer()`, `conflate()`, `onBackpressureDrop()`, etc.)
- **Advantages**
    - Efficient resource reuse
    - Declarative, composable asynchronous flows
    - Built-in flow control and error handling

## **📱 Android Reactive Frameworks**

### **RxJava/RxKotlin**

- Common Operators: map, filter, debounce, merge, flatMap
- Thread Management: subscribeOn, observeOn

### **Kotlin Flow**

- Coroutine integration for simpler asynchronous code.
- Flow Builders & Operators: flow {}, map, filter, combine
- Special Flows: StateFlow, SharedFlow
- Error Handling: catch, retry

---

## **🍏 iOS Reactive Frameworks**

### **RxSwift**

- Core components: Observable, Subject, Single
- Operators: map, flatMap, throttle, combineLatest
- Threading: Use Schedulers effectively

### **Combine**

- Apple’s modern reactive framework.
- Core components: Publisher, Subscriber
- Operators: map, filter, merge, combineLatest
- Thread Management: DispatchQueue, run loops
- Error Handling: catch, retry

---

## **🎯 Practical Cross-Platform Examples**

### **Common Use Cases**

- **API requests and reactive UI updates.**
    - Reduces excessive API calls when the user types rapidly.
    - Smooths UI experience and reduces server load.
    
    ➡️ Uses operators like `debounce`, `distinctUntilChanged`, `filter`.
    
- **Debouncing user input (search).**
    - Automatically updates UI as network or database data changes.
    - Useful for dashboards, profiles, live feeds, etc.
    
    ➡️ Uses `flatMapLatest`, `switchMap`, or `collectLatest`.
    
- **Data synchronization between local and remote sources.**
    - Combines local database data with remote API updates.
    - Shows offline data immediately, then refreshes with network data.
    
    ➡️ Uses `merge`, `combineLatest`, `zip` to coordinate streams.
    
- **Sensor & Real-time Data Streams**
    - Continuously receives sensor data (temperature, GPS, accelerometer).
    - Filters noise, transforms units, updates UI in real time.
    
    ➡️ Uses `filter`, `map`, `throttle`, `combineLatest`.
    
- **Real-Time Form Validation**
    - Validates input fields as the user types.
    - Displays validation results instantly.
    
    ➡️ Uses `combineLatest` to monitor multiple fields simultaneously.
    

### **Side-by-side Code Examples**

- **Use Case 1: Debounced User Input (Search)**
    - Kotlin:
        
        ```
        searchFlow
          .debounce(300)
          .distinctUntilChanged()
          .filter { it.length >= 3 }
          .flatMapLatest { query -> api.search(query) }
          .collect { results -> showResults(results) }
        
        ```
        
    - Swift:
        
        ```
        searchPublisher
          .debounce(for: .milliseconds(300), scheduler: RunLoop.main)
          .removeDuplicates()
          .filter { $0.count >= 3 }
          .flatMap { query in api.search(query) }
          .sink(receiveValue: { results in showResults(results) })
          .store(in: &cancellables)
        
        ```
        
- **Use Case 2: API Requests & Reactive UI Updates**
    - Kotlin:
        
        ```
        viewModelScope.launch {
          api.getUser()
            .flowOn(Dispatchers.IO)
            .collectLatest { user -> updateUI(user) }
        }
        
        ```
        
    - Swift:
        
        ```
        api.getUser()
          .receive(on: DispatchQueue.main)
          .sink(receiveCompletion: { _ in }, receiveValue: { user in updateUI(user) })
          .store(in: &cancellables)
        
        ```
        

---

## **⚠️ Best Practices & Pitfalls**

### **Best Practices**

- **Manage Threading Clearly**
    - Use background threads for heavy work, main thread for UI.
    - Kotlin:
        
        ```
        flow
          .flowOn(Dispatchers.IO)
          .collect { updateUI(it) } // on Main
        
        ```
        
    - Swift:
        
        ```
        publisher
          .subscribe(on: DispatchQueue.global())
          .receive(on: RunLoop.main)
        
        ```
        
- **Always Cancel Subscriptions When No Longer Needed**
    - Avoid memory leaks and zombie collectors.
    - Kotlin:
        
        ```
        val job = launch { flow.collect() }
        
        job.cancel()
        
        ```
        
    - Swift:
    
    ```
    cancellable?.cancel()
    
    ```
    
- **Keep Operator Chains Simple and Readable**
    - Don’t stack too many transformations—each should serve a clear purpose.
    
    ```
    flow.map{}.map{}.filter{}.map{}.filter{}
    
    => NOT GOOD
    
    flow.filter { it > 0 }.map { it * 2 }
    
    => GOOD
    
    ```
    
- **Handle Errors Gracefully**
    - Never ignore errors in async streams.
    - Kotlin
        
        ```
        flow
          .catch { e -> logError(e) }
          .collect { data -> showData(data) }
        
        ```
        
    - Swift
        
        ```
        publisher
          .catch { _ in Just(emptyValue) }
        
        ```
        
- **Use Cold vs Hot Streams Appropriately**
    - Cold streams: re-run logic for each subscriber (e.g., `Flow`, `Publisher`)
    - Hot streams: emit shared values (e.g., `SharedFlow`, `CurrentValueSubject`)

### **Common Pitfalls**

- **Overusing Operators**
    - Excessive chaining (`map`, `filter`, `flatMap`, etc.) reduces readability.
    - Makes debugging and maintenance difficult.
    
    ✅ Keep logic focused, modularize if needed.
    
- **Improper Backpressure Handling (Android)**
    - Emitting data faster than it can be consumed may crash your app (e.g., from sensors or large DB queries).
    
    ✅ Use `buffer()`, `conflate()`, or `collectLatest()` to control flow.
    
- **Memory Leaks Due to Retain Cycles (iOS)**
    - Closures capturing `self` strongly in Combine can leak memory.
    
    ✅ Always use `[weak self]` or `[unowned self]` in `sink`.
    
    ```
    publisher
      .sink { [weak self] value in self?.doSomething() }
    
    ```
    
- **Not Canceling Subscriptions**
    - Leaving `Flow` collectors or `Combine` subscriptions alive can lead to memory leaks or unwanted behavior.
    
    ✅ Cancel with `Job.cancel()` or `cancellable?.cancel()`.
    
- **Using Hot Streams When Cold Is Enough**
    - `SharedFlow`, `Subject`, `CurrentValueSubject` add complexity if you just need one-time data.
    
    ✅ Prefer `Flow`, `Publisher` for simple async operations.
    
- **Misusing UI Thread**
    - Updating UI from background thread causes crashes or undefined behavior.
    
    ✅ Always collect or receive on the main thread.
    

---

## **🛠 Tools & Resources**

- **RxJava (Android):** [GitHub](https://github.com/ReactiveX/RxJava)
- **Kotlin Flow:** [Official Docs](https://kotlinlang.org/docs/flow.html)
- **RxSwift (iOS):** [GitHub](https://github.com/ReactiveX/RxSwift)
- **Combine (Apple):** [Apple Docs](https://developer.apple.com/documentation/combine)

---

## **❓ Q&A Session**

- Address audience questions and real-world challenges.

---

## **❓ Real examples**

### 🛒 **Use Case: Shopify - Reactive Cart Interaction**

**Problem:**
 When a user interacts with the shopping cart (e.g., adds products, changes quantity), the system needs to respond immediately based on inventory availability, promotional pricing, and network status.

**Reactive Approach:**

- **User input**, **inventory state**, and **network status** are each modeled as **independent reactive data streams**.
- These streams are **combined** to automatically drive the UI:
    - Show **out-of-stock** alerts.
    - Update **real-time pricing** based on variant selection or discounts.
    - Disable checkout when the network is offline.
- **Rate-limiting techniques** like **debounce** and **throttle** are applied to reduce API calls during rapid interactions (e.g., pressing "+" repeatedly).

**What Shopify Uses:**

- On **iOS**, Shopify has used **ReactiveSwift** and **Combine** in various products, depending on iOS version support.
- On **Android**, they’ve migrated from **RxJava** to **Kotlin Flow**, leveraging its tight integration with coroutines and lifecycle-awareness.
- In both platforms, Shopify follows **unidirectional data flow (UDF)** with reactive streams driving state changes in the UI.

🖥️ **Use Case: Netflix – Personalized Home Screen**

**Problem:**
 The home screen needs to display personalized content from multiple data sources such as watch history, network status, trending content, and time of day. These data sources update continuously and vary per user and time.

**Reactive Approach:**

- Each data source is modeled as an independent reactive data stream.
- These streams are combined to always produce an updated UI whenever any source changes.
- The movie search feature listens to text input → applies debounce → triggers API calls only after the user stops typing → switches to the latest search result, cancelling any previous requests.

**What Netflix Uses:**

- Netflix leverages RxJava heavily on Android and backend services for reactive streams management.
- They use operators like `combineLatest`, `debounce`, and `switchMap` to efficiently handle multiple data streams and optimize API usage.
- The architecture promotes composable, asynchronous data flows driving the UI reactively.

---

## **🌟 Wrap-up and Key Takeaways**

- Reactive programming improves app responsiveness, simplifies asynchronous logic, and enhances maintainability.
- Encourage experimenting with reactive patterns in projects.

## **🌟 Exercise**

### **Xây dựng tính năng gợi ý tìm kiếm địa điểm (Autocomplete Place Search)**

### **Tình huống**

Bạn đang làm một app di động (Android/iOS) có chức năng tìm kiếm địa điểm. Khi người dùng gõ chữ vào ô tìm kiếm, app phải:

- **Gợi ý kết quả tức thì** từ hai nguồn:
    - **Local cache** (SQLite/Realm) đã lưu sẵn một số địa điểm phổ biến.
    - **Remote API** (ví dụ Google Places API).
- **Giảm thiểu số lượng request** tới server để tiết kiệm băng thông và tránh “spam” API call.
- **Hỗ trợ offline**: nếu đang không có mạng, vẫn hiển thị kết quả từ local cache, và khi có mạng lại sẽ tự động load thêm kết quả mới từ API.
- **Xử lý lỗi**:
    - Khi request đến API thất bại, hiển thị fallback message (ví dụ “Không thể kết nối mạng”), nhưng không crash app.
    - Tự retry với backoff nếu cần.

### **Yêu cầu kỹ thuật**

- **Debounce**: Chỉ gửi sau 300ms kể từ lần nhập cuối cùng.
- **DistinctUntilChanged**: Bỏ qua nếu nội dung query không đổi.
- **Filter**: Chỉ search khi độ dài query ≥ 2 ký tự.
- **FlatMapLatest / switchMap**: Hủy các request cũ ngay khi có query mới.
- **Combine / merge**: Kết hợp stream từ local cache và remote API thành một luồng kết quả duy nhất, ưu tiên hiển thị cache luôn, sau đó update thêm kết quả từ API.
- **Error handling**:
    - `.catch { … }` hoặc `.retryWhen { … }` (trong Kotlin Flow / Combine).
- **Threading**:
    - Thực hiện query local và network trên background (IO), cập nhật UI trên main thread.
- **Lifecycle-awareness**:
    - Tự hủy subscription khi view bị destroy để tránh memory leak.

### **Code**

- Kotlin
    
    ```
    class PlaceSearchViewModel(
        private val api: PlacesApi,
        private val cache: PlacesCache,
        private val networkHelper: NetworkHelper
    ) : ViewModel() {
    
        private val _query = MutableStateFlow<String>("")
        fun setQuery(q: String) { _query.value = q }
    
        val results: StateFlow<List<Place>> = _query
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .flatMapLatest { query ->
                val cacheFlow = flow {
                    emit(cache.searchPlaces(query))
                }.flowOn(Dispatchers.IO)
    
                val remoteFlow = flow {
                    if (!networkHelper.isOnline()) {
                        emit(emptyList())
                    } else {
                        emit(api.searchPlaces(query))
                    }
                }
                .retryWhen { cause, attempt ->
                    if (attempt < 3 && cause is IOException) {
                        val backoff = (500L * (1L shl attempt)).coerceAtMost(4_000L)
                        delay(backoff)
                        true
                    } else false
                }
                .catch { emit(emptyList()) }
                .flowOn(Dispatchers.IO)
    
                cacheFlow.combine(remoteFlow) { cacheList, remoteList ->
                    (cacheList + remoteList).distinctBy { it.id }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    }
    
    ```
    
- Swift
    
    ```
    import Combine
    
    class PlaceSearchViewModel {
        @Published var query: String = ""
        @Published private(set) var results: [Place] = []
    
        private var cancellables = Set<AnyCancellable>()
        private let api: PlacesAPI
        private let cache: PlacesCache
        private let networkMonitor: NetworkMonitor
    
        init(api: PlacesAPI, cache: PlacesCache, networkMonitor: NetworkMonitor) {
            self.api = api
            self.cache = cache
            self.networkMonitor = networkMonitor
    
            Publishers
                .Publishers(for: \.query, on: self)
                .debounce(for: .milliseconds(300), scheduler: RunLoop.main)
                .removeDuplicates()
                .filter { $0.count >= 2 }
                .flatMap { query -> AnyPublisher<[Place], Never> in
                    let cachePublisher = Just(self.cache.searchPlaces(query))
                        .subscribe(on: DispatchQueue.global(qos: .userInitiated))
                        .eraseToAnyPublisher()
    
                    let remotePublisher: AnyPublisher<[Place], Never> = {
                        guard self.networkMonitor.isOnline else {
                            return Just([]).eraseToAnyPublisher()
                        }
                        return self.api.searchPlaces(query)
                            .retry(3)
                            .catch { _ in Just([]) }
                            .subscribe(on: DispatchQueue.global(qos: .userInitiated))
                            .eraseToAnyPublisher()
                    }()
    
                    return cachePublisher
                        .combineLatest(remotePublisher)
                        .map { cacheList, remoteList in
                            Array(Set(cacheList + remoteList))
                        }
                        .eraseToAnyPublisher()
                }
                .receive(on: RunLoop.main)
                .assign(to: \.results, on: self)
                .store(in: &cancellables)
        }
    }
    
    ```
    

### **Câu hỏi thảo luận**

1. **Bạn chọn dùng Hot Flow hay Cold Flow cho phần cache?**
2. **Retry/backoff strategy ra sao?**
3. **Debounce và throttle khác nhau thế nào, khi nào dùng throttle?**
4. **Cách xử lý backpressure (nếu luồng dữ liệu quá nhanh)?**

### **Đáp án**

1. **Bạn chọn dùng Hot Flow hay Cold Flow cho phần cache?**
    1. **Cold Flow** (`flow { … }`) khởi tạo lại mỗi lần có collector, phù hợp khi:
        - Truy vấn cache rất “nhẹ” (SQL đơn giản, dữ liệu đã load sẵn).
        - Mỗi collector muốn chạy logic query mới.
    2. **Hot Flow** (`SharedFlow`/`StateFlow`) phát ngay giá trị mới cho mọi collector, phù hợp khi:
        - Truy vấn cache “nặng” (join nhiều bảng, tính toán phức tạp) — chỉ muốn chạy một lần và chia sẻ kết quả.
        - Muốn “replay” giá trị gần nhất cho collector mới ngay lập tức (ví dụ UI rotate màn hình).
    3. **Kết luận**:
        - Bắt đầu với **Cold Flow** để đơn giản.
        - Nếu gặp **độ trễ cao** hoặc **nhiều collector**, chuyển sang `cacheFlow.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)` hoặc `stateIn(...)` với `replay = 1`.
2. **Retry/backoff strategy ra sao?**
    1. **retryWhen**: chỉ retry khi lỗi do network/IO, không retry lỗi logic (4xx).
    2. **Exponential backoff = chiến lược tăng dần khoảng thời gian chờ giữa các lần retry theo cấp số nhân**
        
        ### Cơ chế chung
        
        Giả sử:
        
        - `BASE_DELAY = 500 ms`
        - Retry lần thứ `n` (bắt đầu từ `n = 0`) sẽ chờ:
        - `delay = BASE_DELAY × 2^n`
        - Ví dụ:
            - Lần retry 0 → chờ 500 ms
            - Lần retry 1 → chờ 1 000 ms
            - Lần retry 2 → chờ 2 000 ms
            - …
3. **Debounce và throttle và sample khác nhau thế nào, khi nào dùng throttle?**
    
    
    | **Tiêu chí** | **Debounce** | **Throttle (throttleFirst)** | **Sample (throttleLast)** |
    | --- | --- | --- | --- |
    | **Cơ chế** | Chờ “im lặng” đủ lâu (khoảng time window) rồi mới emit giá trị cuối cùng. | Ngay khi có giá trị đầu tiên, emit nó, rồi chặn (ignore) tất cả giá trị tiếp theo trong window. | Đánh dấu một khoảng đều đặn (time window), và khi đến cuối mỗi window, emit giá trị mới nhất đã nhận trong window đó. |
    | **Khi emit** | Sau khi chuỗi sự kiện “dừng” ít nhất window. | Ngay lập tức với event đầu, sau đó chặn trong window. | Cuối mỗi window, nếu có ít nhất một event, emit event cuối cùng. |
    | **Giữ lại value** | Chỉ giữ và emit giá trị cuối cùng sau silent period. | Emit giá trị đầu, bỏ tất cả các giá trị kế tiếp trong window. | Emit giá trị kế cuối (latest) trong window, bỏ hết các giá trị khác. |
    1. Khi nào dùng **Throttle (throttleFirst)**
        - **Chống click đúp** trên UI
        - **Giới hạn tần suất gửi sự kiện** (rate limiting) khi bạn muốn xử lý ngay sự kiện đầu và bỏ qua các sự kiện kế tiếp trong một khoảng ngắn.
    2. Khi nào dùng **Debounce**
        - **Autocomplete/ghi chú tìm kiếm**: chờ người dùng nhập xong (“im lặng”) rồi mới call API.
        - **Tránh spam request** khi input liên tục.
    3. Khi nào dùng **Sample**
        - **Lấy mẫu sensor định kỳ** (GPS, accelerometer): mỗi 500 ms lấy giá trị gần nhất.
        - **Cập nhật UI theo khung thời gian cố định** (ví dụ animate, đo tốc độ real-time).
4. **Cách xử lý backpressure (nếu luồng dữ liệu quá nhanh)?**
    1. **buffer()**: tạm lưu các giá trị trong buffer; có thể cấu hình `capacity` và `onBufferOverflow` (DROP_OLDEST / DROP_LATEST).
    2. **conflate()**: chỉ giữ giá trị mới nhất, bỏ qua giá trị cũ khi collector chậm.
    3. **collectLatest { … }**: nếu có emission mới trong khi đang xử lý, huỷ block xử lý cũ và chạy lại với giá trị mới nhất.
    
    ```
    upstreamFlow
      .buffer(capacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST)
      .conflate()
      .collectLatest { value ->
         // Xử lý value (chiếm thời gian)
      }
    
    ```