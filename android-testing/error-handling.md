## Error Handling

- Use try-catch blocks and show error messages to users. -> NOOB

### Use a layered approach with different strategies for different error types:

1. **Network Layer - Structured Error Types**

- Use a sealed class NetworkResult: Success, Error, Loading

**2. Repository Layer - Business Logic Errors**

- Flow Resource

	emit(Resource.Loading())
	val cachedUser = cache.getUser(userId)
    	if (cachedUser != null) {
        		emit(Resource.Success(cachedUser))
    	}
	when NetworkResult.Success -> cache.saveUser(result.data);

emit(Resource.Success(result.data))

**3. UI Layer - User-friendly Error presentation**

- ViewModel:
- Update Ui state with resource + when from Repository
- catch flow from Repository before handle it

**4. Global Error handling**

- FirebaseCrashlytics.getInstance().recordException(e)
- // Restart app gracefully
    	val intent = Intent(context, MainActivity::class.java)
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    	context.startActivity(intent)
    	Process.killProcess(Process.myPid())

## Reference

1. [Cracking Android Interviews: The Questions That Actually Matter (With Real Answers)](https://freedium.cfd/https://abhinay212.medium.com/cracking-android-interviews-the-questions-that-actually-matter-with-real-answers-9a29a66cd878)