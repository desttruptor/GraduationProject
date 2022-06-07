package me.podlesnykh.graduationproject.presentation.models

/**
 * Model to put together search query parameters
 */
data class SourcesSearchSettingsModel(
    val category: String?,
    val language: String?,
    val country: String?
)