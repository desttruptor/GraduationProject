package me.podlesnykh.graduationproject.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class EverythingEntity(
    @PrimaryKey(autoGenerate = true) val numberInTable: Int,
    val publishedAt: String,
    val author: String,
    val urlToImage: String,
    val description: String,
    val source: String,
    val title: String,
    val url: String,
    val content: String
) : DbEntity
