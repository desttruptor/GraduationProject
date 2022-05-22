package me.podlesnykh.graduationproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.podlesnykh.graduationproject.database.entities.ArticleEntity
import me.podlesnykh.graduationproject.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.database.entities.TopHeadlinesEntity

@Database(
    entities = [ArticleEntity::class, SourcesEntity::class, TopHeadlinesEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}