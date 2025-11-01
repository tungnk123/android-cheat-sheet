## Idea behind KMP

- Significant common part between iOS and Android
    - **`Expect/Actual:`**
    - `expect fun getFilePathForFiles(): String`
    → actual on Android and iOS
- Kotlin/Native: targets iOS, macOS, Linux, Windows
    - It defines low-level platform specific details
    - This does not work via expect/actual → but is implemented on the compiler level
    - Raw Kotlin code translates → machine code the native platforms understand
- The driver of every great Multiplatform framework:
**KMP = bundle package that translates common code into platform-specific API**
- KMP libraries: Ktor, Datastore, Room, Compose Multiplatform

## Magic code:

```
Kotlin → JVM bytecode → DEX bytecode → machine code
											→ JVM runtime → machine code → macOS, Linux, Windows
Kotlin → Kotlin IR → LLVM IR (Low-level operations) → Machine code → .framework file
```

- **Kotlin JVM: targets Android, desktop platforms, backend**
    - Android uses ART under the hood
    - JVM receives Kotlin bytecode and translates to machine code

## KMP vs CMP vs Jetpack Compose

- **KMP** = allows implementing platform differences in Kotlin code
- **Jetpack Compose** = UI framework to build UI for Android
- **CMP** = JetBrains extension of Jetpack Compose to support sharing UI between KMP targets

## Compose Multiplatform

- CMP → **Skia** = true engine renderer → platform canvas → platform
- Aims towards feature parity with Jetpack Compose (2-3 months behind)
- Works for Android, iOS and desktop JVM
- Does NOT use native UI widgets
- Does NOT work for desktop native

## KMP vs Flutter vs ReactNative

- Cross-platform:
- Multiplatform:
- No other framework is as flexible as KMP
We get to decide which code is shared and which is implemented truly natively
**Don't need to use Skia → You can build the UI with native widgets and share the rest**
- **KMP**:
    - Compiles to native machine code
    - Direct platform API calls without a bridge layer
    - No native UI widgets in CMP
- **Flutter**:
    - Compiles to native machine code
    - NO native UI widgets (uses Skia just like CMP)
    - Uses a bridge to call platform APIs, well optimized though
- **ReactNative**:
    - JS code runs in a JS engine (Not native)
    - UI uses real native UI widgets

## KMP module

- Example

![image.png](attachment:06384523-3fd1-40ce-997f-d3ea47ecce31:image.png)

- `composeApp` = an application module
- `androidMain`
- `desktopMain`
- `iosMain`

⇒ KMP modules define multiple source sets, each containing an **isolated** part of the module’s code for a specific platform

- The shared module uses Gradle as the build system just like the rest of the project. You can declare **common** and **platform-specific dependencies** using **sourcesets**.
- For example, if your app uses Ktor for networking, you need to add an OkHttp dependency for Android and a darwin dependency for iOS

```kotlin
sourceSets {
   commonMain.dependencies {
       //put your multiplatform dependencies here
       //...
       implementation(libs.ktor.client.core)
       implementation(libs.ktor.client.content.negotiation)
       implementation(libs.ktor.serialization.kotlinx.json)
       //...
   }
   androidMain.dependencies {
       implementation(libs.ktor.client.okhttp)
   }
   iosMain.dependencies {
       implementation(libs.ktor.client.darwin)
   }
}
```

- **Interface in common shared code, implementation in native code (Android or Swift).**
    - In some cases you need to write code that isn't available from KMP code. In this situation you can define an `interface` in `shared` code, `implement` it for Android in Kotlin, and provide an iOS counterpart in Swift.
    - Typically, the **native implementations** are then **injected** into the **shared** code, either by **dependency injection** or directly.
    - This strategy allows for a customized experience on each platform while maintaining a `common` interface for shared code.

## Source set Tree

- Source Set vs Module
    - **Source Set:**
        - Modules consist of one or multiple source sets that contain the actual code for that module
        - Source sets form a **hierarchy** and can be **combined**
    - **Module:**
        - A library
        - Modules can depend on each other to reference their code

![image.png](attachment:e749a2a5-a649-4da2-94d1-afbc76039f18:image.png)

## Who is KMP right for?

- Native android developers → build separate native apps

## **Multiplatform Jetpack libraries**

| **Maven Group ID** | **Latest Update** | **Stable Release** | **Release Candidate** | **Beta Release** | **Alpha Release** | **Documentation** |
| --- | --- | --- | --- | --- | --- | --- |
| [annotation (*)](https://developer.android.com/jetpack/androidx/releases/annotation) | July 16, 2025 | [1.9.1](https://developer.android.com/jetpack/androidx/releases/annotation#annotation-1.9.1) | - | - | - |  |
| [collection](https://developer.android.com/jetpack/androidx/releases/collection) | August 27, 2025 | [1.5.0](https://developer.android.com/jetpack/androidx/releases/collection#1.5.0) | - | - | [1.6.0-alpha01](https://developer.android.com/jetpack/androidx/releases/collection#1.6.0-alpha01) |  |
| [datastore](https://developer.android.com/jetpack/androidx/releases/datastore) | May 20, 2025 | [1.1.7](https://developer.android.com/jetpack/androidx/releases/datastore#1.1.7) | - | - | [1.2.0-alpha02](https://developer.android.com/jetpack/androidx/releases/datastore#1.2.0-alpha02) | [Documentation](https://developer.android.com/kotlin/multiplatform/datastore) |
| [lifecycle (*)](https://developer.android.com/jetpack/androidx/releases/lifecycle) | September 24, 2025 | [2.9.4](https://developer.android.com/jetpack/androidx/releases/lifecycle#lifecycle-*-2.9.4) | - | - | [2.10.0-alpha04](https://developer.android.com/jetpack/androidx/releases/lifecycle#lifecycle-*-2.10.0-alpha04) |  |
| [paging (*)](https://developer.android.com/jetpack/androidx/releases/paging) | September 10, 2025 | [3.3.6](https://developer.android.com/jetpack/androidx/releases/paging#paging-*-3.3.6) | - | - | [3.4.0-alpha04](https://developer.android.com/jetpack/androidx/releases/paging#paging-*-3.4.0-alpha04) |  |
| [room](https://developer.android.com/jetpack/androidx/releases/room) | September 24, 2025 | [2.8.1](https://developer.android.com/jetpack/androidx/releases/room#2.8.1) | - | - | - | [Documentation](https://developer.android.com/kotlin/multiplatform/room) |
| [savedstate](https://developer.android.com/jetpack/androidx/releases/savedstate) | September 17, 2025 | [1.3.3](https://developer.android.com/jetpack/androidx/releases/savedstate#1.3.3) | - | - | [1.4.0-alpha03](https://developer.android.com/jetpack/androidx/releases/savedstate#1.4.0-alpha03) |  |
| [sqlite](https://developer.android.com/jetpack/androidx/releases/sqlite) | September 24, 2025 | [2.6.1](https://developer.android.com/jetpack/androidx/releases/sqlite#2.6.1) | - | - | - | [Documentation](https://developer.android.com/kotlin/multiplatform/sqlite) |

## Set up Jetpack Libraries for KMP

### ViewModel

<aside>
💡

**Note:** ViewModel supports KMP in versions 2.8.0 and higher.

</aside>

- [Set up ViewModel for KMP](https://developer.android.com/kotlin/multiplatform/viewmodel)
- To set up the KMP ViewModel in your project, define the dependency in the **`libs.versions.toml`** file:
    
    ```
    [versions]
    androidx-viewmodel = 2.9.4
    
    [libraries]
    androidx-lifecycle-viewmodel = { module = "androidx.lifecycle:lifecycle-viewmodel", version.ref = "androidx-viewmodel" }
    
    ```
    
- And then add the artifact to the **`build.gradle.kts`** file for your KMP module and declare the dependency as **`api`**, because this dependency will be exported to the binary framework:
    
    ```
    // You need the "api" dependency declaration here if you want better access tto the classes from Swift code.
    commonMain.dependencies {
      api(libs.androidx.lifecycle.viewmodel)
    }
    ```
    
- **CommonViewModel =** The Android [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) approach to building UI can be implemented in common code using Compose Multiplatform.
    
    <aside>
    💡
    
    **Note:** JetBrains also provides a dependency for a [Common ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html#using-viewmodel-in-common-code). This dependency is only required if you want to retrieve a ViewModel in [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/).
    
    </aside>
    
- **Export ViewModel APIs for access from Swift**
    - By default, any library that you add to your codebase won't be automatically exported to the binary framework. If the APIs aren't exported, they are available from the binary framework only if you use them in the shared code (from the **`iosMain`** or **`commonMain`** source set).
    - To improve the experience, you can export the ViewModel dependency to the binary framework using the **`export`** setup in the **`build.gradle.kts`** file where you define the iOS binary framework, which makes the ViewModel APIs accessible directly from the Swift code the same as from Kotlin code
- **Using `viewModelScope` on JVM Desktop**
    - When running coroutines in a ViewModel, the **`viewModelScope`** property is tied to the **`Dispatchers.Main.immediate`**, which might be unavailable on desktop by default. To make it work correctly, add the **`kotlinx-coroutines-swing`** dependency to your project:
        
        ```
        // Optional if you use JVM Desktop
        desktopMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:[KotlinX Coroutines version]")
        }
        
        ```
        
    - See the [**`Dispatchers.Main`** documentation](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) for more details.
- **Use ViewModel from `commonMain` or `androidMain`**
    - There is no specific requirement for using the ViewModel class in the shared **`commonMain`**, nor from the **`androidMain`** sourceSet.
    - The only consideration is you can't use any platform-specific APIs and you need to abstract them.
- **Use ViewModel from SwiftUI**
    - On Android, the ViewModel lifecycle is automatically handled and scoped to a **`ComponentActivity`**, **`Fragment`**, **`NavBackStackEntry`** (Navigation 2), or **`rememberViewModelStoreNavEntryDecorator`** (Navigation 3).
    - SwiftUI on iOS, however, has no built-in equivalent for the AndroidX ViewModel.
    - **Create a function to help with generics**
    - **Connect ViewModel scope to SwiftUI Lifecycle**

### Room Database

### **Not Available in Kotlin Multiplatform**

Some of the APIs that are available on Android are not available in Kotlin Multiplatform.

### **Integration with Hilt**

Because [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) is not available for Kotlin Multiplatform projects, you can't directly use ViewModels with **`@HiltViewModel`** annotation in **`commonMain`** sourceSet. In that case you need to use some alternative DI framework, for example, [Koin](https://insert-koin.io/), [kotlin-inject](https://github.com/evant/kotlin-inject), [Metro](https://zacsweers.github.io/metro/), or [Kodein](https://kosi-libs.org/kodein). You can find all the DI frameworks that work with Kotlin Multiplatform at [klibs.io](https://klibs.io/?query=dependency+injection).

### **Observe Flows in SwiftUI**

Observing coroutines Flows in SwiftUI is not directly supported. However, you can either use [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) or [SKIE](https://skie.touchlab.co/) library to allow this feature.

## Navigation

- [Master Kotlin Multiplication navigation with Decompose — Part 1](https://medium.com/@yeldar.nurpeissov/using-kmp-with-decompose-839dae885c15)
- [Master Kotlin Multiplatform with Decompose — Part 2: Dependency Injection, Kodein, Koin](https://medium.com/@yeldar.nurpeissov/master-kotlin-multiplatform-navigation-with-decompose-add-di-with-kodein-and-koin-405462b2691b)

## Reference

1. [Get started with Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
2. [Kotlin Multiplatform Overview](https://developer.android.com/kotlin/multiplatform)