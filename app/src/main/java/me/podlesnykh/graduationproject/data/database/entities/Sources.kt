package me.podlesnykh.graduationproject.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sources(
    @PrimaryKey(autoGenerate = true)
    val idAg: Int,
    val country: String,
    val name: String,
    val description: String,
    val language: String,
    val id: String,
    val category: String,
    val url: String
)