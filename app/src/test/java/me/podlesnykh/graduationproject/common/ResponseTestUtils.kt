package me.podlesnykh.graduationproject.common

import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines
import me.podlesnykh.graduationproject.data.network.models.*
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import retrofit2.Response

object ResponseTestUtils {

    private const val SAMPLE_TEXT = "sample_text"
    private const val SAMPLE_URL_TO_IMAGE = "sample_url_to_image"
    private const val SAMPLE_URL = "sample_url"

    fun createAllSourcesList() = listOf(
        SourceModel(
            country = "au",
            name = "Australian Financial Review",
            description = SAMPLE_TEXT,
            language = "en",
            id = "australian-financial-review",
            category = "business",
            url = SAMPLE_URL
        ),
        SourceModel(
            country = "us",
            name = "Bloomberg",
            description = SAMPLE_TEXT,
            language = "en",
            id = "bloomberg",
            category = "business",
            url = SAMPLE_URL
        )
    )

    fun createAllArticlesList() = listOf(
        ArticleModel(
            publishedAt = "2022-05-10T16:59:46Z",
            author = "Arielle Pardes",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "Wired",
            title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        ),
        ArticleModel(
            publishedAt = "2022-05-04T12:00:00Z",
            author = "Justine Calma",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "The Verge",
            title = "Why fossil fuel companies see green in Bitcoin mining projects",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        )
    )

    fun createArticlesRetrofitResponse(): Response<ArticlesResponse> =
        Response.success(
            ArticlesResponse(
                status = "ok",
                totalResults = 2,
                articles = listOf(
                    ArticlesItem(
                        publishedAt = "2022-05-10T16:59:46Z",
                        author = "Arielle Pardes",
                        urlToImage = SAMPLE_URL_TO_IMAGE,
                        description = SAMPLE_TEXT,
                        source = createSource("Wired"),
                        title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
                        url = SAMPLE_URL,
                        content = SAMPLE_TEXT
                    ),
                    ArticlesItem(
                        publishedAt = "2022-05-04T12:00:00Z",
                        author = "Justine Calma",
                        urlToImage = SAMPLE_URL_TO_IMAGE,
                        description = SAMPLE_TEXT,
                        source = createSource("The Verge"),
                        title = "Why fossil fuel companies see green in Bitcoin mining projects",
                        url = SAMPLE_URL,
                        content = SAMPLE_TEXT
                    )
                )
            )
        )

    fun createSourcesRetrofitResponse(): Response<SourcesResponse> =
        Response.success(
            SourcesResponse(
                status = "ok",
                sources = listOf(
                    SourcesItem(
                        country = "au",
                        name = "Australian Financial Review",
                        description = SAMPLE_TEXT,
                        language = "en",
                        id = "australian-financial-review",
                        category = "business",
                        url = SAMPLE_URL
                    ),
                    SourcesItem(
                        country = "us",
                        name = "Bloomberg",
                        description = SAMPLE_TEXT,
                        language = "en",
                        id = "bloomberg",
                        category = "business",
                        url = SAMPLE_URL
                    )
                )
            )
        )

    fun createDatabaseEverythingResponse() = listOf(
        Everything(
            numberInTable = 0,
            publishedAt = "2022-05-10T16:59:46Z",
            author = "Arielle Pardes",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "Wired",
            title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        ),
        Everything(
            numberInTable = 1,
            publishedAt = "2022-05-04T12:00:00Z",
            author = "Justine Calma",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "The Verge",
            title = "Why fossil fuel companies see green in Bitcoin mining projects",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        )
    )

    fun createDatabaseTopHeadlinesResponse() = listOf(
        TopHeadlines(
            numberInTable = 0,
            publishedAt = "2022-05-10T16:59:46Z",
            author = "Arielle Pardes",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "Wired",
            title = "Miami’s Bitcoin Conference Left a Trail of Harassment",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        ),
        TopHeadlines(
            numberInTable = 1,
            publishedAt = "2022-05-04T12:00:00Z",
            author = "Justine Calma",
            urlToImage = SAMPLE_URL_TO_IMAGE,
            description = SAMPLE_TEXT,
            source = "The Verge",
            title = "Why fossil fuel companies see green in Bitcoin mining projects",
            url = SAMPLE_URL,
            content = SAMPLE_TEXT
        )
    )

    fun createDatabaseSourcesResponse() = listOf(
        Sources(
            numberInTable = 0,
            country = "au",
            name = "Australian Financial Review",
            description = SAMPLE_TEXT,
            language = "en",
            id = "australian-financial-review",
            category = "business",
            url = SAMPLE_URL
        ),
        Sources(
            numberInTable = 1,
            country = "us",
            name = "Bloomberg",
            description = SAMPLE_TEXT,
            language = "en",
            id = "bloomberg",
            category = "business",
            url = SAMPLE_URL
        )
    )

    private fun createSource(value: String) = Source(
        name = value,
        id = value
    )
}