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