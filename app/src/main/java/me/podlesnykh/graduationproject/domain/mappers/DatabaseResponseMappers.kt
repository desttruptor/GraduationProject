package me.podlesnykh.graduationproject.domain.mappers

import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel

/**
 * Mappers for transforming database responses into data models list
 */
object DatabaseResponseMappers {

    @JvmName("mapEverything")
    fun mapArticlesEntityListToArticlesModelList(inputList: List<EverythingEntity>) =
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
    fun mapArticlesEntityListToArticlesModelList(inputList: List<TopHeadlinesEntity>) =
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

    fun mapSourcesEntityToSourcesModelList(inputList: List<SourcesEntity>) =
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