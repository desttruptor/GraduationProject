package me.podlesnykh.graduationproject.domain.mappers

import com.google.common.truth.Truth
import me.podlesnykh.graduationproject.common.ResponseTestUtils
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers.mapArticlesResponseToArticlesList
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers.mapSourcesResponseToSourcesList
import org.junit.Test

/**
 * Test for [me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers]
 */
class NetworkResponseMappersTest {
    @Test
    fun mapArticlesResponseToArticlesListTest() {
        Truth.assertThat(
            ResponseTestUtils.createAllArticlesList()
        ).isEqualTo(
            mapArticlesResponseToArticlesList(ResponseTestUtils.createArticlesRetrofitResponse())
        )
    }

    @Test
    fun mapSourcesResponseToSourcesListTest() {
        Truth.assertThat(
            ResponseTestUtils.createAllSourcesList()
        ).isEqualTo(
            mapSourcesResponseToSourcesList(ResponseTestUtils.createSourcesRetrofitResponse())
        )
    }
}