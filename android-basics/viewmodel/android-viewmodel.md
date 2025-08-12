## Common questions

1. activityViewModels() vs viewModels(): 
    - both are Property delegate functions (Extension functions for Fragment and ComponentActivity)
    - viewModels():
        - scope: the current Fragment by default
        - each fragments gets its own ViewModel instance
        - Fragment is destroyed -> ViewModel is cleared
    - activityViewModels():
        - scope: the Activity that contains the Fragment
        - All Fragments inside the same Activity will get the same instance of that ViewModel
        - Useful for sharing state between multiple fragments
    - Under the hood:
    1. Find the correct viewModelStoreOwner (Fragment or Activity)
    2. Create or get a ViewModel via ViewModelProvider
    3. Wrap it in a Lazy<T> so it's only created the first time you access it (cached)
    - How to communicate between them?
    1. Global stuff (snackbar, logout, theme, user): use AppBus(Repository) (#2) or Fragment mediator (#1).
    2. Shared flow state across several screens: use shared scope ViewModel (#3).
    3. One-off results: use SavedStateHandle (#4).

## Articles