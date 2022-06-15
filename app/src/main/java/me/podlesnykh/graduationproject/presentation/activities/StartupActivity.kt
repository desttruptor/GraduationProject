package me.podlesnykh.graduationproject.presentation.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import me.podlesnykh.graduationproject.R

/**
 * todo add loading articles for first launch after install
 */
class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        if (isNetworkAvailable()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
        } else {
            Snackbar.make(
                findViewById(R.id.splash_root),
                "Network error: network unavailable",
                6000
            ).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: error("Connectivity manager is null")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
            capabilities?.let {
                when {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                    else -> error("Inconsistent network capabilities checking state")
                }
            } ?: error("Network capabilities are null")
        } else {
            try {
                val activeNetworkInfo = connManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }
}