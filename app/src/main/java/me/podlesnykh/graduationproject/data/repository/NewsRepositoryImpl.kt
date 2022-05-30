package me.podlesnykh.graduationproject.data.repository

import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.data.network.NewsApi
import me.podlesnykh.graduationproject.data.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.data.network.models.SourcesResponse
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getEverythingFromNetwork(
        keyword: String,
        searchIn: String,
        sources: String,
        domains: String,
        excludeDomains: String,
        from: String,
        to: String,
        language: String,
        sortBy: String,
        pageSize: String,
        page: String
    ): Response<ArticlesResponse> =
        newsApi.getEverything(
            keyword, searchIn, sources, domains, excludeDomains, from, to, language, sortBy, pageSize, page
        )

    override suspend fun getTopHeadlinesFromNetwork(
        country: String?,
        category: String?,
        sources: String?,
        q: String,
        pageSize: String,
        page: String
    ): Response<ArticlesResponse> =
        newsApi.getTopHeadlines(
            country, category, sources, q, pageSize, page
        )

    override suspend fun getSourcesFromNetwork(
        category: String,
        language: String,
        country: String
    ): Response<SourcesResponse> =
        newsApi.getSources(
            category, language, country
        )

    override suspend fun getEverythingFromLocal(): List<EverythingEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopHeadlinesFromLocal(): List<TopHeadlinesEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getSourcesFromLocal(): List<SourcesEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun saveEverythingToLocal(everythingList: List<EverythingEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTopHeadlinesToLocal(topHeadlinesList: List<TopHeadlinesEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveSourcesToLocal(sourcesList: List<SourcesEntity>) {
        TODO("Not yet implemented")
    }
}