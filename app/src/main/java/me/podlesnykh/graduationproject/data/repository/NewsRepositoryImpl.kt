package me.podlesnykh.graduationproject.data.repository

import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines
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
        q: String?,
        pageSize: String?,
        page: String?
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
        category: String?,
        language: String?,
        country: String?
    ): Response<SourcesResponse> =
        newsApi.getSources(
            category, language, country
        )

    override suspend fun getEverythingFromLocal(): List<Everything> =
        newsDao.getAllEverything()

    override suspend fun getTopHeadlinesFromLocal(): List<TopHeadlines> =
        newsDao.getAllTopHeadlines()

    override suspend fun getSourcesFromLocal(): List<Sources> =
        newsDao.getAllSources()

    override suspend fun saveEverythingToLocal(everythingList: List<ArticleModel>) {
        newsDao.insertEverything(everythingList)
    }

    override suspend fun saveTopHeadlinesToLocal(topHeadlinesList: List<ArticleModel>) {
        newsDao.insertTopHeadlines(topHeadlinesList)
    }

    override suspend fun saveSourcesToLocal(sourcesList: List<SourceModel>) {
        newsDao.insertSources(sourcesList)
    }

    override suspend fun deleteEverything() {
        newsDao.deleteEverything()
    }

    override suspend fun deleteTopHeadlines() {
        newsDao.deleteTopHeadlines()
    }

    override suspend fun deleteSources() {
        newsDao.deleteSources()
    }
}