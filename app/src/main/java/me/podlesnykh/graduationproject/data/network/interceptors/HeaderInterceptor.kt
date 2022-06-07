package me.podlesnykh.graduationproject.data.network.interceptors

import android.app.Application
import me.podlesnykh.graduationproject.R
import okhttp3.Interceptor
import okhttp3.Response

/**
 * [Interceptor] that appends header with api key to every network request
 */
class HeaderInterceptor(private val application: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .header("X-Api-Key", application.resources.getString(R.string.news_api_key))
                .method(chain.request().method(), chain.request().body())
                .build()
        )
}