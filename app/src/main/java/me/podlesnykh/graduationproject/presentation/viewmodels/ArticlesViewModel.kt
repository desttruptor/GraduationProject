package me.podlesnykh.graduationproject.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.podlesnykh.graduationproject.R
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
    lateinit var application: Application

    @Inject
    lateinit var everythingUseCase: GetEverythingUseCase

    @Inject
    lateinit var topHeadlinesUseCase: GetTopHeadlinesUseCase

    val showProgressLiveData: LiveData<Boolean> get() = _showProgressLiveData
    val articlesListLiveData: LiveData<List<ArticleModel>> get() = _articlesListLiveData
    val errorSnackBarLiveData: LiveData<String> get() = _errorSnackBarLiveData
    val errorDialogLiveData: LiveData<String> get() = _errorDialogLiveData
    val nothingFoundNotification: LiveData<String> get() = _nothingFoundNotification

    private val _showProgressLiveData = MutableLiveData<Boolean>()
    private val _articlesListLiveData = MutableLiveData<List<ArticleModel>>()
    private val _errorSnackBarLiveData = MutableLiveData<String>()
    private val _errorDialogLiveData = MutableLiveData<String>()
    private val _nothingFoundNotification = MutableLiveData<String>()

    fun getEverythingArticlesForce(settingsModel: EverythingSearchSettingsModel) {
        _showProgressLiveData.value = true
        settingsModel.page = "1"
        viewModelScope.launch {
            val response = everythingUseCase.execute(
                settingsModel,
                false,
                isFirstLoad = true
            )
            when (response) {
                is Response.Success -> sendArticles(response)
                is Response.Error -> showError(response, false)
            }
        }
    }

    fun getEverythingArticlesForceNextPage(settingsModel: EverythingSearchSettingsModel) {
        settingsModel.page = (settingsModel.page?.toInt()?.plus(1)).toString()
        viewModelScope.launch {
            val response = everythingUseCase.execute(
                settingsModel,
                false,
                isFirstLoad = false
            )
            when (response) {
                is Response.Success -> sendArticles(response)
                is Response.Error -> showError(response, true)
            }
        }
    }

    fun getTopHeadlinesArticlesForce(settingsModel: TopHeadlinesSearchSettingsModel) {
        _showProgressLiveData.value = true
        settingsModel.page = "1"
        viewModelScope.launch {
            val response = topHeadlinesUseCase.execute(
                settingsModel,
                false,
                isFirstLoad = true
            )
            when (response) {
                is Response.Success -> sendArticles(response)
                is Response.Error -> showError(response, false)
            }
        }
    }

    fun getTopHeadlinesArticlesForceNextPage(settingsModel: TopHeadlinesSearchSettingsModel) {
        settingsModel.page = (settingsModel.page?.toInt()?.plus(1)).toString()
        viewModelScope.launch {
            val response = topHeadlinesUseCase.execute(
                settingsModel,
                false,
                isFirstLoad = false
            )
            when (response) {
                is Response.Success -> sendArticles(response)
                is Response.Error -> showError(response, true)
            }
        }
    }

    fun getEverythingArticlesFromDb() {
        viewModelScope.launch {
            val response = everythingUseCase.execute(
                EverythingSearchSettingsModel(keyword = "bitcoin"),
                true,
                isFirstLoad = false
            )
            when (response) {
                is Response.Success -> sendArticles(response)
                is Response.Error -> {
                    // no notification on loading from database error
                }
            }
        }
    }

    private fun showError(response: Response.Error, isNextPageLoading: Boolean) {
        _showProgressLiveData.value = false
        if (!response.t?.localizedMessage.isNullOrEmpty()) {
            _errorSnackBarLiveData.value = response.t?.localizedMessage
        } else if (response.serverError != null && isNextPageLoading) {
            _errorDialogLiveData.value = response.serverError.message
        } else {
            _errorSnackBarLiveData.value = application.getString(R.string.unknown_error)
        }
    }

    private fun sendArticles(response: Response.Success) {
        _showProgressLiveData.value = false
        if (response.articlesData?.isEmpty() == true) {
            _nothingFoundNotification.value = application.getString(R.string.nothing_found)
        } else {
            _articlesListLiveData.value = response.articlesData
        }
    }
}