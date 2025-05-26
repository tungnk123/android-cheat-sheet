# Broadcast Receiver

*A Broadcast Receiver in Android = component that lets your app listen for system-wide or app-specific messages*

- *These messages, called "**intents**," indicate that an event has happened.*
- When an event happens, the Android system creates a broadcast intent that multiple apps can receive: **System Broadcasts vs Custom Broadcasts**
- *The Broadcast Receiver mechanism in Android uses the **Binder IPC framework** to send and receive messages between processes.*
- *IPC = inter-process communication =describes how different Android components talk to each other*
    - [*Intents](http://developer.android.com/reference/android/content/Intent.html) are messages that components can send and receive.*
    - [*Bundles](http://developer.android.com/reference/android/os/Bundle.html) are data containers passed through intents.*
    - [Binders](http://developer.android.com/reference/android/os/Binder.html) *allow activities and services to get a reference to another service. This lets you not only send messages to services but also call methods on them directly.*
- ***How broadcast works:***
    - ***Sending a Broadcast:** The app or system uses Context.sendBroadcast() or Context.sendOrderedBroadcast() to send a broadcast intent.*
    - ***Queuing in AMS:** The ActivityManagerService (AMS) receives the broadcast request and enqueues it for processing.*
    - ***Finding Registered Receivers:** The system looks up all the receivers registered for the broadcast action. (static: Manifest or dynamic at run time: Context.registerReceiver())*
    - *The **intent** is sent to the **onReceive()** method of each matching receiver on the main thread.*
- **Additional Notes:**
    - **Threading and Performance:** handler.post {} -> run on UI thread
    - **Ordered Broadcasts:** the system processes each receiver sequentially, allowing each to consume or modify the intent before passing it to the next receiver.
    - **Priority and Permissions:** The system can prioritize receivers based on their declared priority
- *Broadcast Receivers can talk to other parts of the app using:*
    - ***Intents**: Send data to an activity or service.*
    - ***LocalBroadcastManager**: For communication within the app to avoid security risks of global broadcasts.*
- **Considerations and Best Practices**
    - **Limit Static Broadcast Receivers**: To save battery, avoid static registration for broadcasts that happen often.
    - **Unregister Dynamic Receivers**: Always unregister dynamic receivers in onPause() or onDestroy() to avoid memory leaks.
    - **Use LocalBroadcastManager**: For in-app broadcasts, use LocalBroadcastManager to improve security and performance.

---

## References:

1. [Understanding Android Broadcast Receivers](https://rommansabbir.com/understanding-android-broadcast-receivers)
2. [Broadcasts overview](https://developer.android.com/develop/background-work/background-tasks/broadcasts)
3. [Broadcasts & Broadcast Receivers - Android Basics 2023](https://www.youtube.com/watch?v=HDVyFsFUuVg)