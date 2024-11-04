1. compileSdkVersion vs minSdkVersion vs targetSdkVersion
⇒ what APIs are available, what the required API level is, and what compatiblity modes are applied
2. **compileSdkVersion** = your way to tell Gradle what version of Android SDK to compile your app with
upper bound of sdk version
changing your compileSdkVersion does not change runtime behavior (not include in apk, manifest)
always compile with the latest SDK
=> new compilcation checks on existing code, avoid newly deprecated API and
be ready to use new APIs
3. **minSdkVersion** = the lower bound for your app
Google Play Store uses to determine which of a user’s devices an app can be installed on
4. **targetSdkVersion** = the main way Android provides forward compatibility by not applying behavior
	 changes unless the targetSdkVersion is updated
=> use new APIs prior to working through the behavior changes
	updating to target the latest SDK should be a high priority
	please, please test before updating your targetSdkVersion

<aside>
💡

minSdkVersion and targetSdkVerision is included in APK, manifest; compliedSdkVersion is not
minSdkVersion <= targetSdkVersion <= compileSdkVersion
minSdkVersion (lowest possible) <= targetSdkVersion == compileSdkVersion (latest SDK)

</aside>