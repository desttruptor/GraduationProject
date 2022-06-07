package me.podlesnykh.graduationproject.presentation.models

/**
 * Source model for presentation layer
 */
data class SourceModel(
    val country: String,
    val name: String,
    val description: String,
    val language: String,
    val id: String,
    val category: String,
    val url: String
)