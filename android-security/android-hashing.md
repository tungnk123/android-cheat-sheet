## Why hashing

- **Properties**
    - Oneway
    - Fixed-length
    - Deterministic
    - Avalanche Effect
- **Usecase in mobile**
    - Integrity checks
    - API request signing
    - Token/signature verification
    - Privacy-preserving identifiers
    - Caching keys / deduplication
- **Hashing vs Encryption**
    
    
    | Hashing | Encryption |
    | --- | --- |
    | 1 way | 2 way |
    | Verify integrity / identity | Protect confidentiality |
    | Fixed length output | Output length depends on input + algorithm |
    | Deterministic | Reversible |
    | SHA-256, SHA-512, BLAKE3 | AES, RSA |
    | Usecase:
    - Password storage (salt + KDF)
    - File integrity checks
    - Cache keys | Usecase:
    - Secure communication
    - Storing sensive data (tokens, credit cards)
    - Encrypted local databases |

## Algorithims and choices

- **Algorithms and choices**
    - SHA-256 – balanced, widely supported(Android/iOS). (Input: 512 bit blocks, Output: 256 bit)
    - SHA-512 – stronger, good for high-security contexts.
    - BLAKE3 – very fast, modern design.
    - HMAC-SHA256 – hash + secret key → API request signing.
    - MD5 – fast but broken (collision attacks).
    - SHA-1 – deprecated, proven vulnerable.
- **Output Encoding:**
    - Hex: human-readable, logs, debugging.
    - Base64: shorter, efficient for headers / tokens.
    - Rule: Always encode input in UTF-8 before
    hashing.

## Android Hashing

- MessageDigest → message hasing
- Mac →
- DigestInputStream → large files

⇒ Use Android KeyStore to store hashing passwords

- Never hardcode keys in app.
- Use Android Keystore → hardware-backed.
- Keys never leave secure hardware.
- Safer than storing in SharedPreferences/files.

- **Best practices:**
    - Charset mismatch → always use UTF-8.
    - Base64 vs Hex → must match server format.
    - Avoid hashing on main thread → move to background.
    - Debug mismatches → usually encoding/charset.

## Secure usage patterns

### Password hashing

- (SHA-256, SHA-512, BLAKE3) are too fast for password storage
- Use password-based key derivation functions (KDFs) which are deliberately slow / memory-hard
    - PBKDF2: proven, widely supported, configurable iteration count.
    - scrypt: adds memory hardness; makes hardware (GPU/ASIC) attacks more expensive.
    - Argon2 (especially Argon2id): winner of the PHC; recommended by OWASP / modern standards.
- When picking KDF parameters, balance security vs UX
- Migration: existing passwords hashed with weaker KDF → rehash on next login to stronger scheme

### Salting + Peppering

- **Salt**: unique, random per password. Stored alongside the hash.
- **Pepper**: a global secret in addition to salt. **Stored separately, not in DB**. Helps defend if DB is leaked.
- Size of salt: typically ≥ 16-32 bytes (128-256 bits) from a
cryptographic random source.

### Constant-time comparisons

- When comparing hash digests / secret tokens, naive == operator or comparisons that bail out on first mismatch leak timing information → Attackers can exploit that.
- Implement or use built-in constant time compare functions (i.e. always iterate over full length, combine results).
- Important when verifying passwords, tokens, HMACs.

### Version your hash scheme

- Include versioning metadata with stored hash (algorithm, iteration count, salt, memory parameters, etc.).

→  when best practices evolve (stronger algorithm, higher iterations), you can upgrade.

- On login (sensitive operation), detect old version → rehash and store new version. (migration)

### API request signing

- Use a **canonical string** to build the inputs for HMAC
- Example: Azure’s REST APIs compose a String-To-Sign = HTTP_METHOD + "\n" + path_and_query +
"\n" + signed_headers_values.
- Compute HMAC-SHA256 over that canonical string using a secret key.
- Encode result in Base64 (or whichever agreed format).
- Send in request, often in a header like Authorization: or Signature
- Backend repeats same process → verifies signature.

### App identity

- Android signing cert SHA-256 fingerprint.
- iOS code signing/trust chain.
- Android:
    - Every APK is signed by a certificate. You can extract the SHA-256 fingerprint of that certificate for use in integrations.
    - E.g. to register API access, configure push notification services, or link app identity with backend
    services.
- iOS:
    - Code signing chain (developer certificate, provisioning profile) gives identity.
    - System ensures that only apps signed with correct certs can install or communicate in certain secure contexts.