package me.podlesnykh.graduationproject.presentation.models

import java.io.Serializable

/**
 * Model to put together search query parameters
 */
data class TopHeadlinesSearchSettingsModel(
    val country: String?,
    val category: String?,
    val sources: String?,
    val q: String?,
    val pageSize: String?,
    var page: String?
) : Serializable