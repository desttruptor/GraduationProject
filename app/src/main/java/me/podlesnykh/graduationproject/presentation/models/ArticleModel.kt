package me.podlesnykh.graduationproject.presentation.models

/**
 * Article model for presentation layer
 */
data class ArticleModel(
    val publishedAt: String,
    val author: String,
    val urlToImage: String,
    val description: String,
    val source: String,
    val title: String,
    val url: String,
    val content: String
)
