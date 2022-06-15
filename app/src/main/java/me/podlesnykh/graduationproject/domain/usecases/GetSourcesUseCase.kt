package me.podlesnykh.graduationproject.domain.usecases

import com.google.gson.Gson
import me.podlesnykh.graduationproject.data.network.models.ErrorResponse
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
        getFromDb: Boolean
    ): Response {
        return if (getFromDb) {
            getFromNetwork(settingsModel)
        } else {
            getFromDatabase()
        }
    }

    private suspend fun getFromDatabase(): Response {
        return try {
            repository.getSourcesFromLocal().let {
                Response.Success(
                    null,
                    DatabaseResponseMappers.mapSourcesEntityToSourcesModelList(it)
                )
            }
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