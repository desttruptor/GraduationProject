package me.podlesnykh.graduationproject.network.utils

import com.google.common.truth.Truth
import me.podlesnykh.graduationproject.presentation.common.utils.NewsApiUtils.encodeUrl
import me.podlesnykh.graduationproject.presentation.common.utils.NewsApiUtils.searchIn
import org.junit.Test

/**
 * Tests for [me.podlesnykh.graduationproject.data.network.utils.NewsApiUtils]
 */
class NewsApiUtilsTest {
    @Test
    fun encodeUrlTest() {
        val stringToEncode = "This, is. A string! to EnCodE"
        val expected = "This%2C+is.+A+string%21+to+EnCodE"
        Truth.assertThat(stringToEncode.encodeUrl()).isEqualTo(expected)
    }

    @Test
    fun searchInTest_none() {
        Truth.assertThat(
            searchIn(mutableListOf(false, false, false))
        ).isEqualTo("")
    }

    @Test
    fun searchInTest_TitleDescription() {
        Truth.assertThat(
            searchIn(mutableListOf(true, true, false))
        ).isEqualTo("title,description")
    }

    @Test
    fun searchInTest_All() {
        Truth.assertThat(
            searchIn(mutableListOf(true, true, true))
        ).isEqualTo("title,description,content")
    }
}
