package me.podlesnykh.graduationproject.common

import io.mockk.coEvery
import io.mockk.mockk
import me.podlesnykh.graduationproject.data.repository.NewsRepository

object MockedNewsRepository {
    val mockedDefaultNewsRepositoryBehavior: NewsRepository = mockk {
        coEvery {
            getEverythingFromNetwork(
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
            getTopHeadlinesFromNetwork(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns ResponseTestUtils.createArticlesRetrofitResponse()

        coEvery {
            getSourcesFromNetwork(
                any(),
                any(),
                any()
            )
        } returns ResponseTestUtils.createSourcesRetrofitResponse()

        coEvery { getEverythingFromLocal() } returns ResponseTestUtils.createDatabaseEverythingResponse()

        coEvery { getTopHeadlinesFromLocal() } returns ResponseTestUtils.createDatabaseTopHeadlinesResponse()

        coEvery { getSourcesFromLocal() } returns ResponseTestUtils.createDatabaseSourcesResponse()

        coEvery { saveEverythingToLocal(ResponseTestUtils.createAllArticlesList()) } returns Unit

        coEvery { saveTopHeadlinesToLocal(ResponseTestUtils.createAllArticlesList()) } returns Unit

        coEvery { saveSourcesToLocal(ResponseTestUtils.createAllSourcesList()) } returns Unit
    }
}