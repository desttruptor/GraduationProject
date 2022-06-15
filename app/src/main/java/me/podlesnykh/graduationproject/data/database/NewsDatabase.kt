package me.podlesnykh.graduationproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines

@Database(
    entities = [Everything::class, Sources::class, TopHeadlines::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}