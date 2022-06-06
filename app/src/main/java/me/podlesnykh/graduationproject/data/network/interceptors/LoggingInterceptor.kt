package me.podlesnykh.graduationproject.data.network.interceptors

import android.util.Log
import me.podlesnykh.graduationproject.data.network.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * [Interceptor] that logs every request and response to network
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().also {
                Log.v(Constants.TAG_REQUEST_LOG, "Sending request to ${it.url()}")
            }
        ).also {
            Log.v(
                Constants.TAG_RESPONSE_LOG,
                "Received response body:\n${it.peekBody(4096).string()}"
            )
        }
}