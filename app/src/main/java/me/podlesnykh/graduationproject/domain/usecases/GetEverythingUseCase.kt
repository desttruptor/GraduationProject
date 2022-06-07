package me.podlesnykh.graduationproject.domain.usecases

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
        isFirstLoad: Boolean
    ): Response {
        return if (isFirstLoad) {
            getFromNetwork(settingsModel)
        } else {
            getFromDatabase(settingsModel)
        }
    }

    private suspend fun getFromDatabase(settingsModel: EverythingSearchSettingsModel): Response {
        return try {
            repository.getEverythingFromLocal()?.let {
                Response.Success(
                    mapArticlesEntityListToArticlesModelList(it),
                    null
                )
            } ?: getFromNetwork(settingsModel)
        } catch (e: Exception) {
            Response.Error(e, null)
        }
    }

    private suspend fun getFromNetwork(settingsModel: EverythingSearchSettingsModel): Response {
        try {
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
                Response.Error(null, fromNetwork.code())
            }
        } catch (e: Exception) {
            return Response.Error(e, null)
        }
    }
}