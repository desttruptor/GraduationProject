package me.podlesnykh.graduationproject.data.network.utils

import me.podlesnykh.graduationproject.data.network.utils.NewsApiUtils.SortBy.PUBLISHED_AT
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Утилитный класс для формирования запросов к Api
 */
object NewsApiUtils {

    /**
     * Кодирует строку в формат для вставки в параметры HTTP-запроса
     */
    fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

    /**
     * Задает зону поиска: искать в названии, описании, содержании статьи
     * Default behaviour: all fields are searched
     *
     * @return [String] with params separated by comma
     */
    fun searchIn(
        title: Boolean = true,
        description: Boolean = true,
        content: Boolean = true
    ): String {
        val sb = StringBuilder()
        val vals = listOf("title", "description", "content")
        val keys = mutableListOf(title, description, content)
        keys.forEachIndexed { index, key ->
            if (key) {
                sb.append(vals[index])
                keys[index] = false
                sb.append(if (keys.contains(true)) "," else "")
            }
        }

        return sb.toString()
    }

    /**
     * Values to define the order to sort the articles
     * [PUBLISHED_AT] is the default option
     */
    enum class SortBy(val value: String) {
        RELEVANCY("relevancy"),
        POPULARITY("popularity"),
        PUBLISHED_AT("publishedAt")
    }
}