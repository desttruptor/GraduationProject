package me.podlesnykh.graduationproject.presentation.models

import java.io.Serializable

/**
 * Model to put together search query parameters
 */
data class EverythingSearchSettingsModel(
    val keyword: String? = null,
    val searchIn: String? = null,
    val sources: String? = null,
    val domains: String? = null,
    val excludeDomains: String? = null,
    val from: String? = null,
    val to: String? = null,
    val language: String? = null,
    val sortBy: String? = null,
    val pageSize: String? = null,
    val page: String? = null
) : Serializable
