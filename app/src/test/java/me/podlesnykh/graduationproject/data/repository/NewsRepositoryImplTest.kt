package me.podlesnykh.graduationproject.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.data.network.NewsApi
import me.podlesnykh.graduationproject.data.network.models.ArticlesItem
import me.podlesnykh.graduationproject.data.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.data.network.models.Source
import me.podlesnykh.graduationproject.data.network.models.SourcesItem
import me.podlesnykh.graduationproject.data.network.models.SourcesResponse
import org.junit.Test
import retrofit2.Response

/**
 * Test for [NewsRepositoryImpl]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {

    private val newsDao: NewsDao = mockk {
        coEvery { deleteSources() } returns Unit
        coEvery { deleteEverything() } returns Unit
        coEvery { deleteTopHeadlines() } returns Unit
        coEvery { insertSources(any()) } returns Unit
        coEvery { insertEverything(any()) } returns Unit
        coEvery { insertTopHeadlines(any()) } returns Unit
        coEvery { getAllSources() } returns createAllSourcesList()
        coEvery { getAllEverything() } returns createAllEverythingList()
        coEvery { getAllTopHeadlines() } returns createAllTopHeadlinesList()
    }
    private val newsApi: NewsApi = mockk {
        coEvery {
            getEverything(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns createArticlesRetrofitResponse()

        coEvery {
            getTopHeadlines(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns createArticlesRetrofitResponse()

        coEvery { getSources(any(), any(), any()) } returns createSourcesRetrofitResponse()
    }

    private val newsRepository = NewsRepositoryImpl(newsDao, newsApi)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEverythingFromNetworkTest() = runTest {

        newsRepository.getEverythingFromNetwork(
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT
        )

        coVerify {
            newsApi.getEverything(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTopHeadlinesFromNetworkTest() = runTest {

        newsRepository.getTopHeadlinesFromNetwork(
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT
        )

        coVerify {
            newsApi.getTopHeadlines(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSourcesFromNetworkTest() = runTest {

        newsRepository.getSourcesFromNetwork(
            SAMPLE_TEXT,
            SAMPLE_TEXT,
            SAMPLE_TEXT
        )

        coVerify {
            newsApi.getSources(
                any(),
                any(),
                any()
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEverythingFromLocalTest() = runTest {

        newsRepository.getEverythingFromLocal()

        coVerify {
            newsDao.getAllEverything()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTopHeadlinesFromLocalTest() = runTest {

        newsRepository.getTopHeadlinesFromLocal()

        coVerify {
            newsDao.getAllTopHeadlines()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSourcesFromLocalTest() = runTest {

        newsRepository.getSourcesFromLocal()

        coVerify {
            newsDao.getAllSources()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveEverythingToLocalTest() = runTest {

        newsRepository.saveEverythingToLocal(createAllEverythingList())

        coVerify {
            newsDao.deleteEverything()
            newsDao.insertEverything(createAllEverythingList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveTopHeadlinesToLocal() = runTest {

        newsRepository.saveTopHeadlinesToLocal(createAllTopHeadlinesList())

        coVerify {
            newsDao.deleteTopHeadlines()
            newsDao.insertTopHeadlines(createAllTopHeadlinesList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveSourcesToLocal() = runTest {

        newsRepository.saveSourcesToLocal(createAllSourcesList())

        coVerify {
            newsDao.deleteSources()
            newsDao.insertSources(createAllSourcesList())
        }
    }

    private fun createAllSourcesList() = listOf(
        SourcesEntity(
            numberInTable = 0,
            country = "au",
            name = "Australian Financial Review",
            description = SAMPLE_TEXT,
            language = "en",
            id = "australian-financial-review",
            category = "business",
            url = SAMPLE_URL
        ),
        SourcesEntity(
            numberInTable = 1,
            country = "us",
            name = "Bloomberg",
            description = SAMPLE_TEXT,
            language = "en",
            id = "bloomberg",
            category = "business",
            url = SAMPLE_URL
        )
    )

    private fun createAllEverythingList() = listOf(
        EverythingEntity(
            numberInTable = 0,
            publishedAt = "2022-05-10T16:59:46Z",
            author = "Arielle Pardes",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "Wired",
            title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        ),
        EverythingEntity(
            numberInTable = 1,
            publishedAt = "2022-05-04T12:00:00Z",
            author = "Justine Calma",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "The Verge",
            title = "Why fossil fuel companies see green in Bitcoin mining projects",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        )
    )

    private fun createAllTopHeadlinesList() = listOf(
        TopHeadlinesEntity(
            numberInTable = 0,
            publishedAt = "2022-05-10T16:59:46Z",
            author = "Arielle Pardes",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "Wired",
            title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        ),
        TopHeadlinesEntity(
            numberInTable = 1,
            publishedAt = "2022-05-04T12:00:00Z",
            author = "Justine Calma",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "The Verge",
            title = "Why fossil fuel companies see green in Bitcoin mining projects",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        )
    )

    private fun createArticlesRetrofitResponse(): Response<ArticlesResponse> =
        Response.success(
            ArticlesResponse(
                status = "ok",
                totalResults = 2,
                articles = listOf(
                    ArticlesItem(
                        publishedAt = "2022-05-10T16:59:46Z",
                        author = "Arielle Pardes",
                        urlToImage = SAMPLE_URL_TO_IMAGE,
                        description = SAMPLE_TEXT,
                        source = createSource("Wired"),
                        title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
                        url = SAMPLE_URL,
                        content = SAMPLE_TEXT
                    ),
                    ArticlesItem(
                        publishedAt = "2022-05-04T12:00:00Z",
                        author = "Justine Calma",
                        urlToImage = SAMPLE_URL_TO_IMAGE,
                        description = SAMPLE_TEXT,
                        source = createSource("The Verge"),
                        title = "Why fossil fuel companies see green in Bitcoin mining projects",
                        url = SAMPLE_URL,
                        content = SAMPLE_TEXT
                    )
                )
            )
        )

    private fun createSourcesRetrofitResponse(): Response<SourcesResponse> =
        Response.success(
            SourcesResponse(
                status = "ok",
                sources = listOf(
                    SourcesItem(
                        country = "au",
                        name = "Australian Financial Review",
                        description = SAMPLE_TEXT,
                        language = "en",
                        id = "australian-financial-review",
                        category = "business",
                        url = SAMPLE_URL
                    ),
                    SourcesItem(
                        country = "us",
                        name = "Bloomberg",
                        description = SAMPLE_TEXT,
                        language = "en",
                        id = "bloomberg",
                        category = "business",
                        url = SAMPLE_URL
                    )
                )
            )
        )

    private fun createSource(value: String) = Source(
        name = value,
        id = value
    )

    companion object {
        private const val SAMPLE_TEXT = "sample_text"
        private const val SAMPLE_URL_TO_IMAGE = "sample_url_to_image"
        private const val SAMPLE_URL = "sample_url"
    }
}