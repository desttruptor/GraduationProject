package me.podlesnykh.graduationproject.network.utils

import com.google.common.truth.Truth
import me.podlesnykh.graduationproject.data.network.utils.NewsApiUtils.encodeUrl
import me.podlesnykh.graduationproject.data.network.utils.NewsApiUtils.searchIn
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
            searchIn(title = false, description = false, content = false)
        ).isEqualTo("")
    }

    @Test
    fun searchInTest_TitleDescription() {
        Truth.assertThat(
            searchIn(content = false)
        ).isEqualTo("title,description")
    }

    @Test
    fun searchInTest_All() {
        Truth.assertThat(
            searchIn()
        ).isEqualTo("title,description,content")
    }
}
