package me.podlesnykh.graduationproject.domain.mappers

import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.ArticlesContainer

object DatabaseResponseMappers {
    @JvmName("mapEverything")
    fun List<EverythingEntity>.mapArticlesEntityListToArticlesContainer() =
        ArticlesContainer(
            totalResults = null,
            articles = this.map {
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
        )

    @JvmName("mapTopHeadlines")
    fun List<TopHeadlinesEntity>.mapArticlesEntityListToArticlesContainer() =
        ArticlesContainer(
            totalResults = null,
            articles = this.map {
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
        )

    fun List<SourcesEntity>.mapSourcesEntityToSourcesModel() =
        this.map {
            SourcesEntity(
                numberInTable = it.numberInTable,
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