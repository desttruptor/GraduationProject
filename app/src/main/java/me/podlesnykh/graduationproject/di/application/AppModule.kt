package me.podlesnykh.graduationproject.di.application

import android.app.Application
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.NewsDatabase
import me.podlesnykh.graduationproject.data.network.Constants
import me.podlesnykh.graduationproject.data.network.Constants.TAG_REQUEST_LOG
import me.podlesnykh.graduationproject.data.network.Constants.TAG_RESPONSE_LOG
import me.podlesnykh.graduationproject.data.network.NewsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {
    @Provides
    @ApplicationScope
    fun provideApplication(application: Application) = application

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(application: Application): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("X-Api-Key", application.resources.getString(R.string.news_api_key))
                    .method(chain.request().method(), chain.request().body())
                    .build()
            )
        }.addInterceptor { chain ->
            val request = chain.request()
            Log.v(TAG_REQUEST_LOG, "Sending request to ${request.url()}")
            val response = chain.proceed(request)
            Log.v(TAG_RESPONSE_LOG, "Received response body:\n${response.body()}")

            return@addInterceptor response
        }.build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.NEWS_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @ApplicationScope
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @ApplicationScope
    fun provideRoom(application: Application): NewsDatabase =
        Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            "news_database"
        ).build()

    @Provides
    @ApplicationScope
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}