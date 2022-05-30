package me.podlesnykh.graduationproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity

@Database(
    entities = [EverythingEntity::class, SourcesEntity::class, TopHeadlinesEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}