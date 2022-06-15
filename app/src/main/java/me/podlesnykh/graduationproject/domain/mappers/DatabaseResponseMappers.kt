package me.podlesnykh.graduationproject.domain.mappers

import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel

/**
 * Mappers for transforming database responses into data models list
 */
object DatabaseResponseMappers {

    @JvmName("mapEverything")
    fun mapArticlesEntityListToArticlesModelList(inputList: List<Everything>) =
        inputList.map {
            ArticleModel(
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                source = it.source,
                title = it.title,
                url = it.url,
                content = it.content
            )
        }

    @JvmName("mapTopHeadlines")
    fun mapArticlesEntityListToArticlesModelList(inputList: List<TopHeadlines>) =
        inputList.map {
            ArticleModel(
                publishedAt = it.publishedAt,
                author = it.author,
                urlToImage = it.urlToImage,
                description = it.description,
                source = it.source,
                title = it.title,
                url = it.url,
                content = it.content
            )
        }

    fun mapSourcesEntityToSourcesModelList(inputList: List<Sources>) =
        inputList.map {
            SourceModel(
                country = it.country,
                name = it.name,
                description = it.description,
                language = it.language,
                id = it.id,
                category = it.category,
                url = it.url
            )
        }
}