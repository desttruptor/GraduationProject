package me.podlesnykh.graduationproject.domain.usecases

import com.google.gson.Gson
import me.podlesnykh.graduationproject.data.network.models.ErrorResponse
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers
import me.podlesnykh.graduationproject.presentation.models.TopHeadlinesSearchSettingsModel

class GetTopHeadlinesUseCase(
    private val repository: NewsRepository
) {
    suspend fun execute(
        settingsModel: TopHeadlinesSearchSettingsModel,
        getFromDb: Boolean,
        isFirstLoad: Boolean
    ): Response {
        return if (getFromDb) {
            getFromNetwork(settingsModel, isFirstLoad)
        } else {
            getFromDatabase()
        }
    }

    private suspend fun getFromDatabase(): Response {
        return try {
            repository.getTopHeadlinesFromLocal().let {
                Response.Success(
                    DatabaseResponseMappers.mapArticlesEntityListToArticlesModelList(it),
                    null
                )
            }
        } catch (e: Exception) {
            Response.Error(e, null)
        }
    }

    private suspend fun getFromNetwork(
        settingsModel: TopHeadlinesSearchSettingsModel,
        isFirstLoad: Boolean
    ): Response {
        try {
            if (isFirstLoad) {
                repository.deleteTopHeadlines()
            }
            val fromNetwork = repository.getTopHeadlinesFromNetwork(
                settingsModel.country,
                settingsModel.category,
                settingsModel.sources,
                settingsModel.q,
                settingsModel.pageSize,
                settingsModel.page
            )
            return if (fromNetwork.isSuccessful) {
                val articlesList =
                    NetworkResponseMappers.mapArticlesResponseToArticlesList(fromNetwork)
                repository.saveTopHeadlinesToLocal(articlesList)
                Response.Success(articlesList, null)
            } else {
                try {
                    val serverError = Gson().fromJson(
                        fromNetwork.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    Response.Error(null, serverError)
                } catch (e: Exception) {
                    Response.Error(e, null)
                }
            }
        } catch (e: Exception) {
            return Response.Error(e, null)
        }
    }
}