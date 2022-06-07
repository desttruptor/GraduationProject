package me.podlesnykh.graduationproject.data

import com.google.common.truth.Truth
import me.podlesnykh.graduationproject.common.ResponseTestUtils
import me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers.mapArticlesEntityListToArticlesModelList
import me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers.mapSourcesEntityToSourcesModelList
import org.junit.Test

/**
 * Test for [me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers]
 */
class DatabaseResponseMappersTest {

    @Test
    fun mapArticlesEntityListToArticlesModelListTest_everything() {
        Truth.assertThat(
            ResponseTestUtils.createAllArticlesList()
        ).isEqualTo(
            mapArticlesEntityListToArticlesModelList(ResponseTestUtils.createDatabaseEverythingResponse())
        )
    }

    @Test
    fun mapArticlesEntityListToArticlesModelListTest_topHeadlines() {
        Truth.assertThat(
            ResponseTestUtils.createAllArticlesList()
        ).isEqualTo(
            mapArticlesEntityListToArticlesModelList(ResponseTestUtils.createDatabaseTopHeadlinesResponse())
        )
    }

    @Test
    fun mapSourcesEntityToSourcesModelListTest() {
        Truth.assertThat(
            ResponseTestUtils.createAllSourcesList()
        ).isEqualTo(
            mapSourcesEntityToSourcesModelList(ResponseTestUtils.createDatabaseSourcesResponse())
        )
    }
}