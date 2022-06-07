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

    @Test
    fun getEverythingFromLocalTest() = runTest {

        newsRepository.getEverythingFromLocal()

        coVerify {
            newsDao.getAllEverything()
        }
    }

    @Test
    fun getTopHeadlinesFromLocalTest() = runTest {

        newsRepository.getTopHeadlinesFromLocal()

        coVerify {
            newsDao.getAllTopHeadlines()
        }
    }

    @Test
    fun getSourcesFromLocalTest() = runTest {

        newsRepository.getSourcesFromLocal()

        coVerify {
            newsDao.getAllSources()
        }
    }

    @Test
    fun saveEverythingToLocalTest() = runTest {

        newsRepository.saveEverythingToLocal(ResponseTestUtils.createAllArticlesList())

        coVerify {
            newsDao.insertEverything(ResponseTestUtils.createAllArticlesList())
        }
    }

    @Test
    fun saveTopHeadlinesToLocalTest() = runTest {

        newsRepository.saveTopHeadlinesToLocal(ResponseTestUtils.createAllArticlesList())

        coVerify {
            newsDao.insertTopHeadlines(ResponseTestUtils.createAllArticlesList())
        }
    }

    @Test
    fun saveSourcesToLocalTest() = runTest {

        newsRepository.saveSourcesToLocal(ResponseTestUtils.createAllSourcesList())

        coVerify {
            newsDao.insertSources(ResponseTestUtils.createAllSourcesList())
        }
    }

    @Test
    fun deleteEverythingTest() = runTest {
        newsRepository.deleteEverything()

        coVerify {
            newsDao.deleteEverything()
        }
    }

    @Test
    fun deleteTopHeadlinesTest() = runTest {
        newsRepository.deleteTopHeadlines()

        coVerify {
            newsDao.deleteTopHeadlines()
        }
    }

    @Test
    fun deleteSourcesTest() = runTest {
        newsRepository.deleteSources()

        coVerify {
            newsDao.deleteSources()
        }
    }

    companion object {
        private const val SAMPLE_TEXT = "sample_text"
    }
}