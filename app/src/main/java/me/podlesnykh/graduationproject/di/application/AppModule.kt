package me.podlesnykh.graduationproject.di.application

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.podlesnykh.graduationproject.network.Constants
import okhttp3.MediaType
import retrofit2.Retrofit

@Module
class AppModule(private val application: Application) {
    @Provides
    @ApplicationScope
    fun provideApplication(application: Application) = application

    @Provides
    @ApplicationScope
    @ExperimentalSerializationApi
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.NEWS_API_BASE_URL)
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()
}