package me.podlesnykh.graduationproject.domain

import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel

/**
 * Sealed class to send data to ViewModel in structured way
 */
sealed class Response {
    data class Success(
        val articlesData: List<ArticleModel>?,
        val sourcesData: List<SourceModel>?
    ) : Response()

    data class Error(
        val t: Throwable?,
        val serverCode: Int?
    ) : Response()
}
