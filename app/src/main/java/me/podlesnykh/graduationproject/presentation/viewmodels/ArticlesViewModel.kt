package me.podlesnykh.graduationproject.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.domain.usecases.GetEverythingUseCase
import me.podlesnykh.graduationproject.domain.usecases.GetTopHeadlinesUseCase
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.EverythingSearchSettingsModel
import me.podlesnykh.graduationproject.presentation.models.TopHeadlinesSearchSettingsModel
import javax.inject.Inject

/**
 * [ViewModel] for [me.podlesnykh.graduationproject.presentation.fragments.ArticlesFragment]
 */
class ArticlesViewModel : ViewModel() {

    @Inject
    lateinit var everythingUseCase: GetEverythingUseCase

    @Inject
    lateinit var topHeadlinesUseCase: GetTopHeadlinesUseCase

    val articlesListLiveData: LiveData<List<ArticleModel>> get() = _articlesListLiveData
    val errorDialogLiveData: LiveData<String> get() = _errorDialogLiveData

    private val _articlesListLiveData: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    private val _errorDialogLiveData: MutableLiveData<String> = MutableLiveData()

    fun getEverythingArticlesForce(settingsModel: EverythingSearchSettingsModel) {
        viewModelScope.launch {
            val response = everythingUseCase.execute(
                settingsModel,
                true
            )
            when (response) {
                is Response.Success -> _articlesListLiveData.value = response.articlesData
                is Response.Error -> _errorDialogLiveData.value = response.t?.localizedMessage
            }
        }
    }

    fun getTopHeadlinesArticlesForce(settingsModel: TopHeadlinesSearchSettingsModel) {
        viewModelScope.launch {
            val response = topHeadlinesUseCase.execute(
                settingsModel,
                true
            )
            when (response) {
                is Response.Success -> _articlesListLiveData.value = response.articlesData
                is Response.Error -> _errorDialogLiveData.value = response.t?.localizedMessage
            }
        }
    }
}