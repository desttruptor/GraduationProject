package me.podlesnykh.graduationproject.domain.usecases

import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.domain.mappers.DatabaseResponseMappers
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers
import me.podlesnykh.graduationproject.presentation.models.SourcesSearchSettingsModel

class GetSourcesUseCase(
    private val repository: NewsRepository
) {
    suspend fun execute(
        settingsModel: SourcesSearchSettingsModel,
        isFirstLoad: Boolean
    ): Response {
        return if (isFirstLoad) {
            getFromNetwork(settingsModel)
        } else {
            getFromDatabase(settingsModel)
        }
    }

    private suspend fun getFromDatabase(settingsModel: SourcesSearchSettingsModel): Response {
        return try {
            repository.getSourcesFromLocal()?.let {
                Response.Success(
                    null,
                    DatabaseResponseMappers.mapSourcesEntityToSourcesModelList(it)
                )
            } ?: getFromNetwork(settingsModel)
        } catch (e: Exception) {
            Response.Error(e, null)
        }
    }

    private suspend fun getFromNetwork(settingsModel: SourcesSearchSettingsModel): Response {
        try {
            val fromNetwork = repository.getSourcesFromNetwork(
                settingsModel.category,
                settingsModel.language,
                settingsModel.country
            )
            return if (fromNetwork.isSuccessful) {
                val sourcesList =
                    NetworkResponseMappers.mapSourcesResponseToSourcesList(fromNetwork)
                repository.saveSourcesToLocal(sourcesList)
                Response.Success(null, sourcesList)
            } else {
                Response.Error(null, fromNetwork.code())
            }
        } catch (e: Exception) {
            return Response.Error(e, null)
        }
    }
}