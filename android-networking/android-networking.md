## Basic knowledge

### OkHttp

### Retrofit

### DNS

- OkHttp use default DNS from system ISP (InetAddress.getAllByName(hostname))
- DNS over HTTPs (DoH) = sends DNS lookup through HTTPs instead of plain text -> no one can intercept or tamper with them easily
    - Add dependency in OkHttp
    - Example
    
    ```jsx
    val cloudfareDns = DnsOverHttps.Builder()
    		.client(boostrapClient)
    		.url("<https://1.1.1.1/dns-query>".toHttpUrl())
    		.build()
    ```
    
- A resolver is like the “DNS agent” that knows where to ask for an IP address.
    - Here you’re telling your fallback agent:
    - “Ask Google first. If Google can’t help, ask Cloudflare.”
    - Example code:
    
    ```jsx
    val googleResolver = SimpleResolver("8.8.8.8")
    val cloudflareResolver = SimpleResolver("1.1.1.1")
    Lookup.setDefaultResolver(ExtendedResolver(arrayOf(googleResolver, cloudflareResolver)))
    ```
    
- Inscreased security:
    - DNS over HTTPs (DoH) like cloudfare DNS, DNS queried
    - ISPs and thirds parties can't easily block and monitor the domains you access
    - Reduced DNS spoofing risk
- More control & customization:
    - choose faster DNS providers (Cloudfare, GoodleDNS) to improve speed
    - use fallback strategies (MultiDNS) if the main DNS fails
- Better user experience: reduced downtime, lower latency
- save DNS lookup into cache file -> increase network speed, reduce DNS request, stable network
- DoH can  bypass ISP DNS blocking

### Apollo

## Best practices

### Best Practices of Retrofit and OkHttp in Android Development

1. Handling Query Parameters in Retrofit: **@Query** vs **@QueryMap**
=> This (@QueryMap) will saves you from writing multiple @Query annotation.
2.  Passing Auth Token
	Dont pass in function -> Use `InterceptorHeader`

1. Logging API Requests & Responses through OkHttp
	Use `LogInterceptor`, ...

2. Use Proper Converters (Not Just Gson)
	⇒ Gson is great, but consider Moshi or Kotlin Serialization for better Kotlin compatibility and performance.

1. Centralize Retrofit Instance (Singleton)
	=> DI with Dagger, Hilt, ...

2. Keep the API Base URL in gradle file
	`buildConfigField("String", "BASE_URL", "\\"<https://apibeta.local.com/\\>"")`
3. Keep the API Endpoints in the Constant File

```jsx
	object ApiConstant {
			private const val V1 = "api/v1/"
			const val LOGIN = "${V1}spacelogin"
	}
	@POST(ApiConstant.LOGIN)
		suspend fun login

```

## Articles

1.