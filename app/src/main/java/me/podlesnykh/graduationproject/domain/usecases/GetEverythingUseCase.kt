package me.podlesnykh.graduationproject.domain.usecases

import com.google.gson.Gson
import me.podlesnykh.graduationproject.data.network.models.ErrorResponse
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers.mapArticlesEntityListToArticlesModelList
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers.mapArticlesResponseToArticlesList
import me.podlesnykh.graduationproject.presentation.models.EverythingSearchSettingsModel

/**
 * Proceeds request to database and network to get list of articles from /everything endpoint
 */
class GetEverythingUseCase(
    private val repository: NewsRepository
) {
    suspend fun execute(
        settingsModel: EverythingSearchSettingsModel,
        getFromDb: Boolean,
        isFirstLoad: Boolean
    ): Response {
        return if (getFromDb) {
            getFromDatabase()
        } else {
            getFromNetwork(settingsModel, isFirstLoad)
        }
    }

    private suspend fun getFromDatabase(): Response {
        return try {
            repository.getEverythingFromLocal().let {
                Response.Success(
                    mapArticlesEntityListToArticlesModelList(it),
                    null
                )
            }
        } catch (e: Exception) {
            Response.Error(e, null)
        }
    }

    private suspend fun getFromNetwork(
        settingsModel: EverythingSearchSettingsModel,
        isFirstLoad: Boolean
    ): Response {
        try {
            if (isFirstLoad) {
                repository.deleteEverything()
            }
            val fromNetwork = repository.getEverythingFromNetwork(
                settingsModel.keyword,
                settingsModel.searchIn,
                settingsModel.sources,
                settingsModel.domains,
                settingsModel.excludeDomains,
                settingsModel.from,
                settingsModel.to,
                settingsModel.language,
                settingsModel.sortBy,
                settingsModel.pageSize,
                settingsModel.page
            )
            return if (fromNetwork.isSuccessful) {
                val articlesList = mapArticlesResponseToArticlesList(fromNetwork)
                repository.saveEverythingToLocal(articlesList)
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