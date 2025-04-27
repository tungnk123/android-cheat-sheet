## **Video**

1. [KotlinConf 2019: Coroutines! Gotta catch 'em all! by Florina Muntenescu & Manuel Vivo](https://www.youtube.com/watch?v=w0kfnydnFWI&t=12s&ab_channel=JetBrains)
2. https://www.youtube.com/watch?v=yc_WfBp-PdE&ab_channel=PhilippLackner

## **Articles**

1. [Coroutines: first things first](https://medium.com/androiddevelopers/coroutines-first-things-first-e6187bf3bb21)
2. https://medium.com/@af2905g/kotlin-coroutines-interview-questions-youll-actually-get-asked-dac79ec3aa18
3. https://levelup.gitconnected.com/tricky-android-interview-questions-kotlin-coroutines-edition-449e951742c8

## Random note

1. **The real difference between Job.cancel() and Scope.cancel()**
    - A **Job** represents a single coroutine. It’s returned by launch or async, and gives you control over that specific coroutine — you can cancel it, check its status, or wait for it to finish.
    - A CoroutineScope, on the other hand, is a container for coroutines. It holds a CoroutineContext, which usually includes a Job that manages the lifecycle of all coroutines launched within the scope.
    - The CoroutineScope interface itself does not define a cancel() function.
    Once a CoroutineScope is cancelled (i.e., its internal Job is cancelled):
    	Any new launch {} or async {} calls will not run
    	The scope is considered inactive
    	This behavior is especially important in Android components like ViewModel or Service, where coroutine scopes are tied to the component’s lifecycle
    => **job.cancel(), scope.cancel(), use lifecycle-aware scopes that are cancelled automatically**
    - coroutineScope {} suspends the parent until all its children finish
2. Structured concurrency ensures safety - but it can confuse you if you’re not watching coroutine lifecycles closely.
3. **Launch and async**
    - launch {} and async {} are extension functions on CoroutineScope.
    - They are NOT suspend functions, meaning you don't need to be inside a suspend function to call them.

```
-> launch and async start a coroutine immediately when you call them.

You can call launch or async from any normal function, no need for suspend.

delay(), coroutineScope{}, withContext(): SUSPEND FUNCTIONS
-> Must be called inside a suspend function or coroutine

coroutineScope is a suspend function
-> only return when all children coroutine is finished

```

4. Nature of suspend function
    - A suspend function = function that can pause (suspend) and resume execution without blocking the thread
    - coroutineScope {} is a special suspend function.
    It does two important things:
        - It creates a parent coroutine.
        - Inside, you can launch multiple child coroutines.
    
    => coroutineScope {} will suspend itself and NOT return until ALL its child coroutines are completed.
    => It makes concurrency safe and structured (no abandoned coroutines).