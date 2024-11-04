1. Context = the bridge between components
=> communicate between components, instatiate components, access components
- Instantiate components -> Activity, Fragment, Service, Broadcast Receiver
- Access Components -> access system service, filesystem, resource
- Communicate components -> access components from another app, use intent-filter

2. UI context vs non-UI context:
only ContextThemeWrapper is UI context -> Context + Theming
=> use UI context when inflate any XML, your views are themed
=> inflate layout with Non-UI context your layout will not be themed

```
UI context = Activity + getContext() in Fragment + getContext() in View if view was constructed using
	UI-context

Non-Ui context = anything which is not ContextThemeWrapper (Application, Service, BroadcastReceiver	)
=> it can do almost everything UI context can do but we lose theme
VD: getApplicationContext(), application as applicationContext

```

=> inflate a layout -> use a UI context, Non-UI context is bearable
=> UI context can access resources and file system
=> Pass UI-context to somewhere where all it needs is resource access or file system but it is a LONG OPERATION IN THE BACKGROUND => Memory leak
Pass Reference of UI-context to long living  objects => Memory leak

3. short-living context = Activity context = UI context
4. Access UI related stuff, inflate layout => Use UI-context
otherwise, use Non-Ui context
Make sure you do not pass short-living context to long-living objects
5. this vs getContext() vs getBaseContext() vs getApplicationContext():
- this = Activity itself => UI context, short-living context
- getApplicationContext() = application itself => non-UI context, long-living context
- getBaseContext() = get the base context of the current class
6. Memory Leak = view model hold a instance of activity -> Garbage Collector dont collect the instance because
view model is still living
7. Activity context -> tied to the lifecycel of activity
=> working with UI components or resources that tied to a specific activity
=> inflating layouts, create dialogs, start new activity, service
Application context -> lifecycle of the application
=> access context outside of a activity (singleton, service, broadcast receiver)
8. Retained References:
when a long-lived object (static variable, singleton) hold a reference to an actiivty context
=> retain a reference to the entire activity and all of its objects (views, fragments, resources,
layout, drawables)
=> activity cannot be garbage collected => memory leak
=> refrain from storing activity contexts in long-lived objects
9. How to prevent memory leaks:
    - Use weak references instead of strong references
    - Release activity contexts properly
    - Clean up resources and callbacks