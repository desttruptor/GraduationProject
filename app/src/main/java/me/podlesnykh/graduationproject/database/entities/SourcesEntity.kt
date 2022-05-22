package me.podlesnykh.graduationproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class SourcesEntity(
    @PrimaryKey(autoGenerate = true) val numberInTable: Int,
    val country: String?,
    val name: String?,
    val description: String?,
    val language: String?,
    val id: String?,
    val category: String?,
    val url: String?
)