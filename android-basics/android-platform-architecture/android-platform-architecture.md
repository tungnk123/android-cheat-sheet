# Android Platform Architecture

### Linux Kernel

The foundation of the Android platform is the Linux kernel. It provides core system services such as threading and low-level memory management. It also offers security features and enables hardware drivers.

### Hardware Abstraction Layer (HAL)

HAL provides standard interfaces to expose hardware capabilities (e.g., camera, Bluetooth). Each HAL module implements an interface for a hardware component.

### Android Runtime (ART)

From Android 5.0+, apps run in their own process with their own instance of ART. It executes DEX files and supports Ahead-of-Time (AOT) and Just-in-Time (JIT) compilation, optimized garbage collection, and better debugging. ART replaced Dalvik as the runtime.

### Native C/C++ Libraries

Many Android services (e.g., ART, HAL) are built from native code. Apps can access native libraries like OpenGL ES or SQLite via Java APIs, or directly through the NDK.

### Java API Framework

Provides the entire Android OS feature set through Java APIs. Includes UI components, ResourceManager, NotificationManager, ActivityManager, and ContentProviders.

### System Apps

Android includes core apps (e.g., Phone, SMS, Browser). They function as both user apps and providers of system capabilities, and can often be replaced by third-party apps.

### Summary of Major Components

- **Application (System Apps):** Home, Contacts, Phone, Browser, Camera, Calendar, Clock
- **Application Framework:** ActivityManager, WindowManager, ContentProviders, ViewSystem, ResourceManager, LocationManager
- **Native C/C++ Libs:** SQLite, OpenGL, Webkit, SSL, etc.
- **Runtime:** Dalvik + ART, Zygote
- **HAL:** Audio, Bluetooth, Camera, Sensors
- **Linux Kernel: core**

![image.png](https://developer.android.com/static/guide/platform/images/android-stack_2x.png)

# Dalvik, JIT, AOT, and ART

- **JIT (Just-In-Time):** Compile bytecode → machine code *at runtime*.
- **AOT (Ahead-Of-Time):** Compile bytecode → machine code *before runtime* (usually at install).
- **Dalvik:** Android’s old runtime using JIT only.
- **ART:** New runtime using a mix of JIT + AOT.

## **Comparison Table**

| Feature / Runtime | **Dalvik** | **JIT (concept)** | **AOT (concept)** | **ART (Android Runtime)** |
| --- | --- | --- | --- | --- |
| **Introduced** | Android 1.0 – 4.4 (default) | General JVM technique | General compilation strategy | Android 4.4 (opt-in), default since 5.0 |
| **When compiled** | At runtime (JIT) | At runtime | At install time | Mix: JIT + AOT |
| **App install speed** | Fast (no precompile) | Fast | Slower (compile upfront) | Medium (JIT first, AOT later) |
| **App startup speed** | Slower (needs JIT each time) | Slower | Very fast (already compiled) | Fast (JIT improves first run, AOT optimizes hot paths) |
| **Runtime performance** | Moderate | Moderate | High | High (adaptive, improves over time) |
| **Battery/CPU usage** | Higher (repeated JIT) | Higher | Lower | Lower (optimized) |
| **Storage usage** | Small (DEX only) | Small | Larger (stores native code) | Moderate (hybrid compiled code) |

---

👉 **In short:**

- **Dalvik = JIT only** → small apps, but slower runtime.
- **Pure JIT** → flexible, but inefficient for repeated execution.
- **Pure AOT** → fast execution, but heavy install + storage.
- **ART = hybrid JIT + AOT** → balance of performance, battery, and size.