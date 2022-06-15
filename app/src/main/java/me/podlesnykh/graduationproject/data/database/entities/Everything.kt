package me.podlesnykh.graduationproject.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Everything(
    @PrimaryKey(autoGenerate = true)
    val idAg: Int,
    val publishedAt: String,
    val author: String,
    val urlToImage: String,
    val description: String,
    val source: String,
    val title: String,
    val url: String,
    val content: String
)