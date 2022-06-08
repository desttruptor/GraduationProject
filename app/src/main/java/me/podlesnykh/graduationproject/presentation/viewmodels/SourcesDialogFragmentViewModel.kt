package me.podlesnykh.graduationproject.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.podlesnykh.graduationproject.domain.Response
import me.podlesnykh.graduationproject.domain.usecases.GetSourcesUseCase
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import me.podlesnykh.graduationproject.presentation.models.SourcesSearchSettingsModel
import javax.inject.Inject

/**
 * Viewmodel for [me.podlesnykh.graduationproject.presentation.fragments.SourcesDialogFragment]
 *
 * TODO IMPLEMENT
 */
class SourcesDialogFragmentViewModel : ViewModel() {
    @Inject
    lateinit var useCase: GetSourcesUseCase

    val sourcesListLiveData: LiveData<List<SourceModel>> get() = _sourcesListLiveData
    val errorDialogLiveData: LiveData<String> get() = _errorDialogLiveData

    private val _sourcesListLiveData: MutableLiveData<List<SourceModel>> = MutableLiveData()
    private val _errorDialogLiveData: MutableLiveData<String> = MutableLiveData()

    fun getSources() {
        viewModelScope.launch {
            val response = useCase.execute(
                SourcesSearchSettingsModel(null, null, null),
                true
            )
            when (response) {
                is Response.Success -> _sourcesListLiveData.value = response.sourcesData
                is Response.Error -> _errorDialogLiveData.value = response.t?.localizedMessage
            }
        }
    }
}