package me.podlesnykh.graduationproject.presentation.models

/**
 * Model for list of articles and some useful data
 */
data class ArticlesContainer(
    val totalResults: Int?,
    val articles: List<ArticleModel>
)