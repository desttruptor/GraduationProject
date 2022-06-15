package me.podlesnykh.graduationproject.data.repository

import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines
import me.podlesnykh.graduationproject.data.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.data.network.models.SourcesResponse
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import retrofit2.Response

/**
 * Repository for interaction with database and network
 */
interface NewsRepository {

    /**
     * @return [Response] on request to /everything endpoint
     *
     * @param keyword Keywords or phrases to search for in the article title and body.
     * @param searchIn The fields to restrict your q search to. The possible options are: title, description, content.
     * Multiple options can be specified by separating them with a comma
     * @param sources A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     * @param domains A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to search to.
     * @param excludeDomains A comma-seperated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to exclude from results.
     * @param from A date and optional time for the oldest article allowed.
     * @param to A date and optional time for the newest article allowed.
     * @param language The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * @param sortBy The order to sort the articles in. Possible options: relevancy, popularity, publishedAt.
     * @param pageSize The number of results to return per page. Default: 100, maximum: 100.
     * @param page Use this to page through the results.
     */
    suspend fun getEverythingFromNetwork(
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
    ): Response<ArticlesResponse>

    /**
     * @return [Response] on request to /top-headlines endpoint
     * Do not mix "country" & "category" with "sources" parameters
     *
     * @param country The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     * @param category The category you want to get headlines for.
     * @param sources A comma-seperated string of identifiers for the news sources or blogs you want headlines from.
     * @param q Keywords or a phrase to search for.
     * @param pageSize The number of results to return per page (request). 20 is the default, 100 is maximum.
     * @param page Use this to page through the results if the total results found is greater than the page size.
     */
    suspend fun getTopHeadlinesFromNetwork(
        country: String?,
        category: String?,
        sources: String?,
        q: String?,
        pageSize: String?,
        page: String?
    ): Response<ArticlesResponse>

    /**
     * @return [Response] on request to /top-headlines/sources endpoint
     *
     * @param category Find sources that display news of this category.
     * Possible options: business, entertainment, general, health, science, sports, technology.
     * @param language Find sources that display news in a specific language. Default value: all languages
     * @param country Find sources that display news in a specific country.
     */
    suspend fun getSourcesFromNetwork(
        category: String?,
        language: String?,
        country: String?
    ): Response<SourcesResponse>

    /**
     * @return [List] of [Everything] from database
     */
    suspend fun getEverythingFromLocal(): List<Everything>

    /**
     * @return [List] of [TopHeadlines] from database
     */
    suspend fun getTopHeadlinesFromLocal(): List<TopHeadlines>

    /**
     * @return [List] of [Sources] from database
     */
    suspend fun getSourcesFromLocal(): List<Sources>

    /**
     * @param everythingList list of articles to insert
     * Insert [List] of [ArticleModel] into database
     */
    suspend fun saveEverythingToLocal(everythingList: List<ArticleModel>)

    /**
     * @param topHeadlinesList list of articles to insert
     * Insert [List] of [ArticleModel] into database
     */
    suspend fun saveTopHeadlinesToLocal(topHeadlinesList: List<ArticleModel>)

    /**
     * @param sourcesList list of sources to insert
     * Insert [List] of [SourceModel] into database
     */
    suspend fun saveSourcesToLocal(sourcesList: List<SourceModel>)

    /**
     * Function to delete everything from "Everything" table in DB
     */
    suspend fun deleteEverything()

    /**
     * Function to delete everything from "Top headlines" table in DB
     */
    suspend fun deleteTopHeadlines()

    /**
     * Function to delete everything from "Sources" table in DB
     */
    suspend fun deleteSources()
}