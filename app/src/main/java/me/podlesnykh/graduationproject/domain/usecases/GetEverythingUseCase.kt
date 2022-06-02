package me.podlesnykh.graduationproject.domain.usecases

import kotlinx.coroutines.CoroutineScope
import me.podlesnykh.graduationproject.data.repository.NewsRepository
import me.podlesnykh.graduationproject.domain.mappers.NetworkResponseMappers
import me.podlesnykh.graduationproject.presentation.models.ArticleModel

class GetEverythingUseCase(
    private val repository: NewsRepository,
    private val mapper: NetworkResponseMappers,
    private val externalCoroutineScope: CoroutineScope
) {

}