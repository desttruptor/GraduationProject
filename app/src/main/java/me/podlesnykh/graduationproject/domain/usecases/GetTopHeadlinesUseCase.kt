package me.podlesnykh.graduationproject.domain.usecases

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
        isFirstLoad: Boolean
    ): Response {
        return if (isFirstLoad) {
            getFromNetwork(settingsModel)
        } else {
            getFromDatabase(settingsModel)
        }
    }

    private suspend fun getFromDatabase(settingsModel: TopHeadlinesSearchSettingsModel): Response {
        return try {
            repository.getTopHeadlinesFromLocal()?.let {
                Response.Success(
                    DatabaseResponseMappers.mapArticlesEntityListToArticlesModelList(it),
                    null
                )
            } ?: getFromNetwork(settingsModel)
        } catch (e: Exception) {
            Response.Error(e, null)
        }
    }

    private suspend fun getFromNetwork(settingsModel: TopHeadlinesSearchSettingsModel): Response {
        try {
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
                Response.Error(null, fromNetwork.code())
            }
        } catch (e: Exception) {
            return Response.Error(e, null)
        }
    }
}