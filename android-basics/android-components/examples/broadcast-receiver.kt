// ConnectivityReceiver.kt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            Toast.makeText(context, "Connected to the internet", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Disconnected from the internet", Toast.LENGTH_SHORT).show()
        }
    }
}

// Android Manifest
<receiver android:name=".ConnectivityReceiver">
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
</receiver>