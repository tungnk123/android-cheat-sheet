The `AndroidManifest.xml` file is one of the most important files in any Android application. It defines critical information about your app, such as its components, permissions, and configuration. Below is a detailed overview of everything you need to know about the `AndroidManifest.xml` file.

---

### 1. **Structure of `AndroidManifest.xml`**

The manifest file follows a hierarchical structure, and the core components of the file include:

- **`<manifest>` tag**: The root tag of the manifest file, which defines the XML namespace and the package name of the app.
- **`<uses-permission>`**: Declares the permissions required by the app.
- **`<application>`**: Contains information about the app itself, including its components (Activities, Services, etc.).
- **`<activity>`, `<service>`, `<receiver>`, `<provider>`**: Define the app's components.
- **`<uses-sdk>`**: Specifies the minimum and target SDK versions.
- **`<intent-filter>`**: Defines the actions, categories, and data types that an app component can handle.

### 2. **Manifest Elements**

### 2.1. **`<manifest>` Tag**

The `<manifest>` tag is the root element of the Android manifest file. It contains the package name of the app, which is used to uniquely identify it on the device and the Google Play Store.

Example:

```xml
<manifest xmlns:android="<http://schemas.android.com/apk/res/android>"
    package="com.example.myapp">
    <!-- Content here -->
</manifest>

```

- **`xmlns:android`**: Declares the XML namespace for Android.
- **`package`**: The package name of the app (must be unique).

### 2.2. **`<uses-permission>` Tag**

The `<uses-permission>` tag is used to declare the permissions the app needs to function. These permissions can include access to the internet, camera, location, and more.

Example:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

```

- **`android:name`**: The permission name (e.g., `android.permission.INTERNET`).

### 2.3. **`<application>` Tag**

The `<application>` tag contains information about the application as a whole. This tag is the parent of all other components such as activities, services, and more.

Attributes of `<application>`:

- **`android:icon`**: Specifies the app icon.
- **`android:label`**: Specifies the name of the app, typically shown on the device's home screen.
- **`android:theme`**: Specifies the theme used throughout the app.
- **`android:name`**: Can be used to specify a custom `Application` class.

Example:

```xml
<application
    android:icon="@drawable/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/AppTheme">
    <!-- Components go here -->
</application>

```

### 2.4. **Defining Components**

Within the `<application>` tag, you define the components of your app, such as **Activities**, **Services**, **Broadcast Receivers**, and **Content Providers**.

### 2.4.1. **`<activity>` Tag**

The `<activity>` tag defines an activity in the app. An activity represents a single screen in your app.

Attributes:

- **`android:name`**: The name of the activity class.
- **`android:label`**: Specifies the label (name) of the activity.
- **`android:exported`**: For apps targeting Android 12 and later, indicates whether an activity can be launched by other apps.

Example:

```xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>

```

- **`intent-filter`**: Declares the types of intents that the activity can handle, such as `MAIN` for the main entry point, or custom actions.

### 2.4.2. **`<service>` Tag**

The `<service>` tag defines a service, which is a component that runs in the background to perform long-running operations.

Example:

```xml
<service android:name=".MyService" />

```

### 2.4.3. **`<receiver>` Tag**

The `<receiver>` tag defines a broadcast receiver, which listens for and reacts to broadcast messages from other apps or the system.

Example:

```xml
<receiver android:name=".BootReceiver">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>

```

### 2.4.4. **`<provider>` Tag**

The `<provider>` tag defines a content provider, which allows data sharing between apps.

Example:

```xml
<provider android:name=".MyContentProvider"
    android:authorities="com.example.myapp.provider" />

```

### 2.5. **`<uses-sdk>` Tag**

The `<uses-sdk>` tag defines the minimum and target SDK versions for your app.

Attributes:

- **`android:minSdkVersion`**: The minimum API level required to run the app.
- **`android:targetSdkVersion`**: The API level on which the app is tested to run.

Example:

```xml
<uses-sdk
    android:minSdkVersion="21"
    android:targetSdkVersion="30" />

```

### 2.6. **`<intent-filter>` Tag**

The `<intent-filter>` tag is used to define which intents an app component can handle. It is often used inside activities, services, or broadcast receivers.

Example:

```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:scheme="http" />
</intent-filter>

```

### 3. **Other Important Elements**

### 3.1. **`<uses-feature>` Tag**

The `<uses-feature>` tag declares hardware or software features required by the app, such as camera, Bluetooth, or multitouch support.

Example:

```xml
<uses-feature android:name="android.hardware.camera" android:required="true" />

```

### 3.2. **`<meta-data>` Tag**

The `<meta-data>` tag is used to store additional data in your app, often used for integrating third-party services.

Example:

```xml
<meta-data
    android:name="com.example.API_KEY"
    android:value="your_api_key_here" />

```

### 3.3. **`<supports-screens>` Tag**

The `<supports-screens>` tag defines which screen sizes and densities the app supports.

Example:

```xml
<supports-screens
    android:smallScreens="true"
    android:normalScreens="true"
    android:largeScreens="true"
    android:xlargeScreens="true"
    android:resizeable="true"
    android:largeHeap="true" />

```

### 4. **Versioning and Updating**

In addition to setting the min SDK version, you may need to define version information in the manifest for versioning and updates:

- **`android:versionCode`**: The internal version number used to differentiate versions.
- **`android:versionName`**: The user-visible version name displayed in the app store.

Example:

```xml
<manifest xmlns:android="<http://schemas.android.com/apk/res/android>"
    android:versionCode="1"
    android:versionName="1.0">
    <!-- Other content -->
</manifest>

```

---

### 5. **Important Considerations**

- **Exported Components**: For Android 12 (API 31) and later, you must declare whether activities, services, or broadcast receivers are "`exported`" or not. This is a security measure to prevent external apps from launching components in your app.
- **SDK version**: [compileSdkVersion vs targetSdkVersion vs minSdkVersion](https://www.notion.so/12f6beb34dd1808ab980e9d023d19168?pvs=21)
- **Permissions**: Always request only the permissions your app truly needs to enhance privacy and user trust. For sensitive permissions, you may need to request them at runtime in addition to declaring them in the manifest.
- **Intent Filters**: Properly configuring `intent-filters` allows your app to integrate with other apps (e.g., handling shared content or URL schemes).

---

### Conclusion

The `AndroidManifest.xml` is the cornerstone of an Android app. It defines critical details about your app, from components and permissions to SDK versions and app features. Understanding how to configure this file is essential for building a robust Android app.

---

**More:**

- https://medium.com/@kjaiz411/all-about-android-manifest-file-bb69f9df5cc6
- https://medium.com/dev-genius/understanding-android-permissions-29e9f9175dd8