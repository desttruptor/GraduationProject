package me.podlesnykh.graduationproject.presentation.common.utils

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
    fun searchIn(keys: List<Boolean>): String {
        val sb = StringBuilder()
        val vals = listOf("title,", "description,", "content,")
        keys.forEachIndexed { index, key ->
            if (key) {
                sb.append(vals[index])
            }
        }
        return if (
            sb.isNotEmpty() &&
            sb.toString().elementAt(sb.toString().length - 1) == ','
        ) {
            sb.toString().substring(0, sb.toString().length - 1)
        } else {
            sb.toString()
        }
    }
}