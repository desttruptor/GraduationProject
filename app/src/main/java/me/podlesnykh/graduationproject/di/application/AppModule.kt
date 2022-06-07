package me.podlesnykh.graduationproject.di.application

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.NewsDatabase
import me.podlesnykh.graduationproject.data.network.Constants
import me.podlesnykh.graduationproject.data.network.NewsApi
import me.podlesnykh.graduationproject.data.network.interceptors.HeaderInterceptor
import me.podlesnykh.graduationproject.data.network.interceptors.LoggingInterceptor
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.data.repository.NewsRepositoryImpl
import me.podlesnykh.graduationproject.domain.usecases.GetEverythingUseCase
import me.podlesnykh.graduationproject.domain.usecases.GetTopHeadlinesUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {
    @Provides
    @ApplicationScope
    fun provideApplication() = application

    @Provides
    @ApplicationScope
    fun provideLoggingInterceptor() = LoggingInterceptor()

    @Provides
    @ApplicationScope
    fun provideHeaderInterceptor(application: Application) = HeaderInterceptor(application)

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

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

    @Provides
    @ApplicationScope
    fun provideNewsRepository(dao: NewsDao, api: NewsApi): NewsRepository =
        NewsRepositoryImpl(dao, api)

    @Provides
    @ApplicationScope
    fun provideGetEverythingUseCase(repository: NewsRepository) = GetEverythingUseCase(repository)

    @Provides
    @ApplicationScope
    fun provideGetTopHeadlinesUseCase(repository: NewsRepository) =
        GetTopHeadlinesUseCase(repository)
}