## **Android Background Work – Comparison Table**

| API / Mechanism | When to Use | Guarantees | Limitations |
| --- | --- | --- | --- |
| **WorkManager** | Reliable background jobs that must **eventually run** (e.g., sync data, upload logs). Supports constraints (Wi-Fi, charging, battery). | Survives app kill & reboot. Ensures completion. | Not for real-time or exact-timing tasks. Min 15 min interval for periodic work. |
| **Coroutines (Lifecycle/ViewModel scope)** | Short tasks tied to UI or component lifecycle (e.g., API call, DB query). | Lightweight, cancel automatically with scope. | Dies if app killed. No persistence. Not for guaranteed background work. |
| **Foreground Service** | Long-running **user-visible** work (e.g., music, file download, tracking). | Keeps running with a persistent notification. | Heavy on battery; must show notification. User may stop it. |
| **AlarmManager** | Schedule tasks at **exact time** (e.g., alarm clock, reminder). | Can wake device at specific clock time. | No guarantee of job completion logic. Best for *triggering* work, not heavy tasks. |
| **JobScheduler** | System-level job scheduling (API 21+). | Handles batching, power-optimized. | Replaced by WorkManager (which wraps it). Rarely used directly now. |
| **Handler/HandlerThread** | Simple delayed or background tasks inside app process. | Easy for lightweight, short-lived tasks. | Process-bound. Dies if app killed. |

---

✅ **Rule of Thumb**

- **WorkManager** → default choice for most **background jobs**.
- **Coroutines** → UI & lifecycle-bound async work.
- **Foreground Service** → long-running + user-visible.
- **AlarmManager** → exact time alarms/reminders

## Articles

1. [WorkManager in 2025: 5 Patterns That Actually Work in Production](https://freedium.cfd/https://medium.com/@hiren6997/workmanager-in-2025-5-patterns-that-actually-work-in-production-fde952c0d095)