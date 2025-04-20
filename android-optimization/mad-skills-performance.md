1. **Performance metrics**
    - app startup
    - smooth experience
    - power saving
    - smart data usage
    - small app size
    - crash free
2. **Inspect -> Improve -> Monitor**
3. **AppStartup**
    - Cold start -> Warm start -> Hot start
    - **Cold start**:
        - create process
        - Application#onCreate
        - -> first time launch app, cache is empty -> longest phase
    - **Warm start**:
        - inflate view hierarchy
        - Activity#onCreate
        - -> when comeback from a background
    - **Hot Start**:
        - Draw first frame
        - Activity#onStart
        - -> when go to foreground

- **Time To Initial Display = TTID** = default app startup end state = reported when first frame of your application is drawn
    - When? -> first frame of application is drawn
    - How to see? -> ActivityTaskManager: Displayed in logcat
- **Time To Full Display = TTFD** = optional metric that can use to further optimize your app startup time
    - When? -> data is loaded, user can start interacting
    - How to see? -> ActivityTaskManager: Fully drawn
1. **Smooth experience**
    - **Behavior Jank** = happens when an app is too busy to meet the frame timing window
        - When? -> App is too busy doing work on the main thread and missing the timing window to draw a frame on screen
        - -> scrolling, content is moved on screen, ...
    - **Frame rate** = the amount of redraws per second
    	default for android: 60fps
    - **Tip**: **Keeping as much work as possible off the main thread**
    - Always measure realease builds
    	`isShrinkResources` = true
    	`isMinifyEnabled` = true
    - **Measure performance twice or more**

---

### Video:

- [Performance: Important metrics - MAD Skills](https://www.youtube.com/watch?v=r5AseKQh2ZE&list=PLWz5rJ2EKKc91i2QT8qfrfKgLNlJiG1z7&index=25&ab_channel=AndroidDevelopers)