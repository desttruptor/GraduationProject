package me.podlesnykh.graduationproject.domain.mappers

import me.podlesnykh.graduationproject.data.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.data.network.models.SourcesResponse
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import retrofit2.Response

object NetworkResponseMappers {
    fun mapArticlesResponseToArticlesList(response: Response<ArticlesResponse>): List<ArticleModel> =
        response.body()?.let {
            it.articles?.map { articlesItem ->
                ArticleModel(
                    publishedAt = articlesItem.publishedAt ?: "",
                    author = articlesItem.author ?: "",
                    urlToImage = articlesItem.urlToImage ?: "",
                    description = articlesItem.description ?: "",
                    source = articlesItem.source?.name ?: "",
                    title = articlesItem.title ?: "",
                    url = articlesItem.url ?: "",
                    content = articlesItem.content ?: ""
                )
            } ?: emptyList()
        } ?: emptyList()

    fun mapSourcesResponseToSourcesList(response: Response<SourcesResponse>): List<SourceModel> =
        response.body()?.let {
            it.sources?.map { sourcesItem ->
                SourceModel(
                    country = sourcesItem.country ?: "",
                    name = sourcesItem.name ?: "",
                    description = sourcesItem.description ?: "",
                    language = sourcesItem.language ?: "",
                    id = sourcesItem.id ?: "",
                    category = sourcesItem.category ?: "",
                    url = sourcesItem.url ?: ""
                )
            }
        } ?: emptyList()
}