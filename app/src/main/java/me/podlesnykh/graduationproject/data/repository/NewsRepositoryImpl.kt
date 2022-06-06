package me.podlesnykh.graduationproject.data.repository

import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.data.network.NewsApi
import me.podlesnykh.graduationproject.data.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.data.network.models.SourcesResponse
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getEverythingFromNetwork(
        keyword: String?,
        searchIn: String?,
        sources: String?,
        domains: String?,
        excludeDomains: String?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: String?,
        page: String?
    ): Response<ArticlesResponse> =
        newsApi.getEverything(
            keyword,
            searchIn,
            sources,
            domains,
            excludeDomains,
            from,
            to,
            language,
            sortBy,
            pageSize,
            page
        )

    override suspend fun getTopHeadlinesFromNetwork(
        country: String?,
        category: String?,
        sources: String?,
        q: String,
        pageSize: String,
        page: String
    ): Response<ArticlesResponse> =
        if (sources != null) {
            newsApi.getTopHeadlines(
                "", "", sources, q, pageSize, page
            )
        } else if (country != null && category != null) {
            newsApi.getTopHeadlines(
                country, category, "", q, pageSize, page
            )
        } else {
            newsApi.getTopHeadlines(
                "", "", "", q, pageSize, page
            )
        }

    override suspend fun getSourcesFromNetwork(
        category: String,
        language: String,
        country: String
    ): Response<SourcesResponse> =
        newsApi.getSources(
            category, language, country
        )

    override suspend fun getEverythingFromLocal(): List<EverythingEntity>? =
        newsDao.getAllEverything()

    override suspend fun getTopHeadlinesFromLocal(): List<TopHeadlinesEntity>? =
        newsDao.getAllTopHeadlines()

    override suspend fun getSourcesFromLocal(): List<SourcesEntity>? =
        newsDao.getAllSources()

    override suspend fun saveEverythingToLocal(everythingList: List<ArticleModel>) {
        newsDao.deleteEverything()
        newsDao.insertEverything(everythingList)
    }

    override suspend fun saveTopHeadlinesToLocal(topHeadlinesList: List<ArticleModel>) {
        newsDao.deleteTopHeadlines()
        newsDao.insertTopHeadlines(topHeadlinesList)
    }

    override suspend fun saveSourcesToLocal(sourcesList: List<SourceModel>) {
        newsDao.deleteSources()
        newsDao.insertSources(sourcesList)
    }
}