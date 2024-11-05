4 components:

- Activity
    - An **Activity** represents a single screen with a user interface.
    - It acts as an entry point for a user to interact with the application.
    - Lifecycle methods like `onCreate()`, `onStart()`, `onResume()`, `onPause()`, `onStop()`, and `onDestroy()` are used to manage the activity's behavior.
- Service
    - A **Service** is a component for long-running background tasks that don’t require a user interface.
    - Services are typically used for operations like playing music, handling network transactions, or interacting with content providers.
    - Types include:
        - **Foreground Services**: Services visible to the user (e.g., playing music).
        - **Background Services**: Services that operate without user interaction.
        - **Bound Services**: Services bound to a component, often for inter-process communication.
- Broadcast Receiver
    - A **Broadcast Receiver** responds to system-wide broadcast announcements (e.g., battery level, Wi-Fi status changes).
    - It listens for specific events and can perform actions based on the received intents.
    - Broadcast Receivers are primarily used for event-driven functionality, such as responding to connectivity changes.
    - *A broadcast receiver is implemented as a subclass of **BroadcastReceiver.** Each broadcast is delivered as an [`Intent`](https://developer.android.com/reference/android/content/Intent) object.*
- Content Provider
    - A **Content Provider** manages access to a structured set of data, allowing data sharing between different applications.
    - Used to access data stored in files, SQLite databases, or on the web.
    - It provides a standard way for applications to query or modify data in other apps, using URIs for access control.

---

**More:**

- https://developer.android.com/guide/topics/providers/content-providers
- https://medium.com/@huseyinozkoc/what-are-the-main-application-components-in-android-development-cab07d395074
- [https://medium.com/@ramadan123sayed/the-main-components-of-android-a-simple-guide-617c96681689#:~:text=These main components — Activities%2C Services,blocks of any Android app](https://medium.com/@ramadan123sayed/the-main-components-of-android-a-simple-guide-617c96681689#:~:text=These%20main%20components%20%E2%80%94%20Activities%2C%20Services,blocks%20of%20any%20Android%20app).