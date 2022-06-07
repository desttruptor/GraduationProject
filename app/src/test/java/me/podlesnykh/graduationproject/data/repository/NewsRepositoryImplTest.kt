package me.podlesnykh.graduationproject.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.podlesnykh.graduationproject.common.ResponseTestUtils
import me.podlesnykh.graduationproject.data.database.NewsDao
import me.podlesnykh.graduationproject.data.network.NewsApi
import org.junit.Test

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
        coEvery { getAllSources() } returns ResponseTestUtils.createDatabaseSourcesResponse()
        coEvery { getAllEverything() } returns ResponseTestUtils.createDatabaseEverythingResponse()
        coEvery { getAllTopHeadlines() } returns ResponseTestUtils.createDatabaseTopHeadlinesResponse()
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
        } returns ResponseTestUtils.createArticlesRetrofitResponse()

        coEvery {
            getTopHeadlines(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()

        coEvery {
            getSources(
                any(),
                any(),
                any()
            )
        } returns ResponseTestUtils.createSourcesRetrofitResponse()
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

        newsRepository.saveEverythingToLocal(ResponseTestUtils.createAllArticlesList())

        coVerify {
            newsDao.deleteEverything()
            newsDao.insertEverything(ResponseTestUtils.createAllArticlesList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveTopHeadlinesToLocal() = runTest {

        newsRepository.saveTopHeadlinesToLocal(ResponseTestUtils.createAllArticlesList())

        coVerify {
            newsDao.deleteTopHeadlines()
            newsDao.insertTopHeadlines(ResponseTestUtils.createAllArticlesList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveSourcesToLocal() = runTest {

        newsRepository.saveSourcesToLocal(ResponseTestUtils.createAllSourcesList())

        coVerify {
            newsDao.deleteSources()
            newsDao.insertSources(ResponseTestUtils.createAllSourcesList())
        }
    }

    companion object {
        private const val SAMPLE_TEXT = "sample_text"
    }
}