## Basic knowledge

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

## Articles