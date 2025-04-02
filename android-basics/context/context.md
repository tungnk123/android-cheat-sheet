1. Context = the **bridge** between components =  object that provides information about the **environment** in which the application is currently running
    
    = **bridge** between an Android app and the operating system by providing access to various resources, services, and **system-level operations**.
    
    => communicate between components, instatiate components, access components
    
- Instantiate components -> Activity, Fragment, Service, Broadcast Receiver, inflating layouts
- Access Components -> access system service, filesystem, resource, request permissions,
- Communicate components -> access components from another app, use intent-filter
1. UI context vs non-UI context:
only ContextThemeWrapper is UI context -> Context + Theming
=> use UI context when inflate any XML, your views are themed
=> inflate layout with Non-UI context your layout will not be themed

```
UI context = Activity + getContext() in Fragment + getContext() in View if view was constructed using UI-context

Non-Ui context = anything which is not ContextThemeWrapper (Application, Service, BroadcastReceiver	)
=> it can do almost everything UI context can do but we lose theme
VD: getApplicationContext(), application as applicationContext

```

=> inflate a layout -> use a UI context, Non-UI context is bearable
=> UI context can access resources and file system
=> Pass UI-context to somewhere where all it needs is resource access or file system but it is a LONG OPERATION IN THE BACKGROUND => Memory leak
Pass Reference of UI-context to long living  objects => Memory leak

1. short-living context = Activity context = UI context
2. Access UI related stuff, inflate layout => Use UI-context
otherwise, use Non-Ui context
Make sure you do not pass short-living context to long-living objects
3. this vs getContext() vs getBaseContext() vs getApplicationContext():
- this = Activity itself => UI context, short-living context
- getApplicationContext() = application itself => non-UI context, long-living context
- getBaseContext() = get the base context of the current class; use with ContextWrapper to get the base context of the ContextWrapper (Don’t use this. It is a code smell)
1. Memory Leak = view model hold a instance of activity -> Garbage Collector dont collect the instance because
view model is still living
2. Activity context -> tied to the lifecycel of activity
=> working with UI components or resources that tied to a specific activity
=> inflating layouts, create dialogs, start new activity, service
Application context -> lifecycle of the application
=> access context outside of a activity (singleton, service, broadcast receiver)
3. Retained References:
when a long-lived object (static variable, singleton) hold a reference to an actiivty context
=> retain a reference to the entire activity and all of its objects (views, fragments, resources,
layout, drawables)
=> activity cannot be garbage collected => memory leak
=> refrain from storing activity contexts in long-lived objects
4. How to prevent memory leaks:
    - Use weak references instead of strong references
    - Release activity contexts properly
    - Clean up resources and callbacks

---

**More**

- https://medium.com/@sevbanbuyer/context-in-android-97eda93dc777
- https://medium.com/free-code-camp/mastering-android-context-7055c8478a22
- https://medium.com/@husayn.fakher/understanding-context-in-android-development-a-comprehensive-guide-bdb0affacd6f