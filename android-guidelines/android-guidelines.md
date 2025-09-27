## Document

1. [Kotlin documentation guidelines](https://docs.google.com/document/d/1mUuxK4xwzs3jtDGoJ5_zwYLaSEl13g_SuhODdFuh2Dc/edit?tab=t.0)
2. [kotlin-multiplatform-dev-docs](https://github.com/JetBrains/kotlin-multiplatform-dev-docs)

## Plugins

1. [10 Must-Have Android Studio Plugins to Supercharge Your Development 🚀](https://towardsdev.com/10-must-have-android-studio-plugins-to-supercharge-your-development-51437855c1f1)
2. [Top 5 Most Useful Plugins For Android Studio](https://freedium.cfd/https://medium.com/codeverse-chronicles/top-5-most-useful-plugins-for-android-studio-0319de850e17)

## JVM Options, Heap size

### What each setting controls

- **IDE VM Options (Help → Edit Custom VM Options)**
    
    Configures the **Android Studio/IntelliJ process** only (indexing, UI, code analysis).
    
    Example: `-Xmx4096m -Xms1024m -XX:MaxMetaspaceSize=1g`
    
- **`org.gradle.jvmargs` in `gradle.properties`**
    
    Configures the **Gradle daemon JVM** (your build process).
    
    Example: `-Xms512m -Xmx4096m -Dfile.encoding=UTF-8 -XX:+UseG1GC`
    
- **`kotlin.daemon.jvmargs` (optional)**
    
    Configures the **Kotlin compiler daemon** only.
    
    Example: `-Xmx2048m`
    

These three are **independent** and can use **different JDKs** (see *Settings → Build Tools → Gradle → Gradle JDK*).

### Meaning of `Xms/-Xmx`

- `Xms` = initial **heap** size for that JVM.
- `Xmx` = **max heap** size for that JVM.
- Applies to the **specific process** (IDE, Gradle, or Kotlin daemon), **not** your whole laptop.

### Safe, modern defaults

```
org.gradle.jvmargs=-Xms512m -Xmx4096m -Dfile.encoding=UTF-8 -XX:+UseG1GC
# If Kotlin compiles OOM:
kotlin.daemon.jvmargs=-Xmx2048m
```

**RAM-based hints**

- 8 GB RAM → Gradle `Xmx 2048–3072m`
- 16 GB RAM → `Xmx 4096–6144m`
- 32 GB+ → `Xmx 8192m`+

### Common pitfalls & fixes

- **JDK 17 + Gradle 7.2 crash**: `XX:+CMSClassUnloadingEnabled` is invalid →
    
    **Fix**: remove that flag and upgrade wrapper to **Gradle 8.x** (or at least 7.6.4).
    
    Command: `./gradlew wrapper --gradle-version 8.9 --distribution-type all`
    
- Check and clean bad flags from:
    - Project `gradle.properties`
    - `~/.gradle/gradle.properties`
    - Env vars: `GRADLE_OPTS`, `JAVA_TOOL_OPTIONS`

That’s it: IDE options = memory for the IDE; `org.gradle.jvmargs` = memory for the build; Kotlin daemon has its own knob.

---

## **References:**

- https://github.com/mastani/android-convention-cheatsheet?tab=readme-ov-file
- https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
- https://github.com/ribot/android-guidelines/tree/master