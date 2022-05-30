package me.podlesnykh.graduationproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity

@Dao
interface NewsDao {
    @Insert
    suspend fun insertEverything(everythingList: List<EverythingEntity>)

    @Insert
    suspend fun insertTopHeadlines(topHeadlinesList: List<TopHeadlinesEntity>)

    @Insert
    suspend fun insertSources(sourcesList: List<SourcesEntity>)

    @Query("DELETE FROM ARTICLES")
    suspend fun deleteEverythingArticles()

    @Query("DELETE FROM TOP_HEADLINES")
    suspend fun deleteTopHeadlines()

    @Query("DELETE FROM SOURCES")
    suspend fun deleteSources()

    @Query("SELECT * FROM ARTICLES")
    suspend fun getAllEverythingArticles(): List<EverythingEntity>

    @Query("SELECT * FROM TOP_HEADLINES")
    suspend fun getAllTopHeadlines(): List<TopHeadlinesEntity>

    @Query("SELECT * FROM SOURCES")
    suspend fun getAllSources(): List<SourcesEntity>
}