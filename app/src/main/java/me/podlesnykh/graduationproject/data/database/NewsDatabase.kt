package me.podlesnykh.graduationproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.podlesnykh.graduationproject.data.database.entities.ArticleEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity

@Database(
    entities = [ArticleEntity::class, SourcesEntity::class, TopHeadlinesEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}