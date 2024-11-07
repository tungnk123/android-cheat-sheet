In Android development, **tasks**, **back stacks**, and **launch modes** are foundational concepts that control how activities are launched and managed in memory. Understanding them helps create predictable navigation flows and optimize app behavior.

---

### 1. Tasks and Back Stacks

A **task** in Android is a collection of activities that users interact with as a cohesive unit. Each app can have multiple tasks, and each task is typically associated with a specific user goal, such as viewing email, composing a message, or browsing a web page. **Tasks** are often created when users start a new app from the launcher or interact with an activity that is assigned to start a new task.

### Back Stack

The **back stack** is a last-in, first-out (LIFO) structure that holds the sequence of activities the user has navigated. When an activity is launched, it’s added to the task’s back stack. Pressing the **Back** button on a device or using certain programmatic methods (like `finish()`) pops the top activity off the stack, returning to the previous activity.

- **Root Activity**: The first activity in a task’s back stack.
- **Standard Activity Behavior**: If `Activity A` starts `Activity B`, then pressing the back button in `Activity B` will return to `Activity A` by popping `Activity B` from the stack.

> Example: If a user launches an email app, reads an email, and then opens a link within it, they might go through several activities (like EmailList, EmailDetail, and WebView). The back stack will be WebView -> EmailDetail -> EmailList.
> 

---

### 2. Launch Modes

**Launch modes** determine how activities are instantiated and organized in a task. They influence the task’s back stack and affect navigation. Android has four launch modes that can be set using the `android:launchMode` attribute in the manifest or by using specific flags in the intent.

### Launch Modes:

1. **standard** (default)
    - A new instance of the activity is created every time it’s launched.
    - Multiple instances of the same activity can exist in the same or different tasks.
    - Ideal for activities that don’t require a single instance, like lists or detail views.
2. **singleTop**
    - If an instance of the activity already exists at the top of the back stack, Android will route the intent to this existing instance (instead of creating a new one).
    - Useful for activities that don’t need multiple instances when they're already visible, like search or home screens.
    - If the instance is reused, `onNewIntent()` is called to handle the intent.
3. **singleTask**
    - Ensures that only one instance of the activity exists across all tasks.
    - If an instance already exists, Android routes the intent to that instance.
    - A new task is created when the activity is launched if it doesn’t already exist in a task.
    - Ideal for "entry-point" activities, such as a main screen or dashboard.
4. **singleInstance**
    - The activity is placed in a new task, and no other activities can be launched into this task.
    - Only one instance of the activity exists, and it exists in isolation.
    - Commonly used for activities that should act independently, like a media player or incoming call screen.

> Example Scenario: An activity that represents a login page might use singleTask so that it always routes back to the same instance when re-opened, regardless of the user's position in the app.
> 

---

### 3. Task Affinities

**Task Affinity** is a way to specify which task an activity should belong to. By default, all activities in an app have the same affinity, which means they belong to the same task. However, you can assign a custom affinity to an activity using the `taskAffinity` attribute in the manifest.

- **Custom Affinity**: Activities with different affinities can open in different tasks.
- **Use Cases**: Custom affinities are useful for launching activities in different tasks intentionally, like starting a new task for a "Help" or "Settings" screen that users might want to access from multiple places.

---

### 4. Intent Flags for Controlling Task and Back Stack

You can also use **intent flags** to influence task and back stack behavior. These are particularly useful when launching activities programmatically.

### Common Intent Flags:

- **FLAG_ACTIVITY_NEW_TASK**: Creates a new task and starts the activity at the root of that task. This flag is often used with `singleTask` and `singleInstance` launch modes.
- **FLAG_ACTIVITY_CLEAR_TOP**: Clears all activities on top of the target activity in the back stack if it already exists. This can be combined with `FLAG_ACTIVITY_NEW_TASK` to bring an existing activity to the foreground and clear any intermediate activities.
- **FLAG_ACTIVITY_SINGLE_TOP**: Behaves like the `singleTop` launch mode but can be used selectively.
- **FLAG_ACTIVITY_CLEAR_TASK**: Clears all activities from the current task and launches the activity as the new root.

> Example: If the user is viewing a product page and wants to return to the home screen, you could use FLAG_ACTIVITY_CLEAR_TOP to pop the product and any intermediate pages from the back stack, returning the user to the home screen with minimal back-stack history.
> 

---

### 5. Task and Back Stack Navigation with Jetpack Navigation Component

In modern Android apps, especially those using Jetpack, **Jetpack Navigation Component** helps manage tasks, back stacks, and deep links while simplifying navigation.

### Navigation Component Key Features:

- **NavController**: Controls app navigation, managing the back stack for fragments, and simplifying transitions.
- **Navigation Graph**: Defines navigation routes and associated arguments in XML or programmatically.
- **Deep Linking**: Allows launching specific screens within a task via URLs or explicit intents.
- **Back Stack Management**: Automatically manages back stack, reducing the need for manual intent flags or custom back stack handling.

> Example: Using the Navigation Component, you can define a "home" screen as the default start destination and easily add deep links for specific destinations, like profile or settings, with automatic handling of the back stack.
> 

---

### Summary Table: Launch Modes and Their Effects

| Launch Mode | Description | Task Behavior | Common Use Case |
| --- | --- | --- | --- |
| **standard** | Default mode, new instance each time | Same task or a new task depending on the launcher | Detail screens, content pages |
| **singleTop** | No new instance if already on top of back stack | Same task | Search, home, or recent views |
| **singleTask** | Single instance in task; new task if not already in one | Launches activity as root in a new task if needed | Login, main dashboard |
| **singleInstance** | Only one instance, runs in an isolated task | Isolated task | Media player, phone call screen |

Each mode and feature offers flexibility in managing activity lifecycle and navigation. Choosing the right configuration for each activity ensures smoother user experience and efficient resource usage.

---

**More:**

- https://medium.com/@riztech.dev/understanding-android-launch-modes-and-tasks-3397c3065fef
- https://developer.android.com/guide/components/activities/tasks-and-back-stack