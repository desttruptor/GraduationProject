package me.podlesnykh.graduationproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.podlesnykh.graduationproject.database.entities.ArticleEntity
import me.podlesnykh.graduationproject.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.database.entities.TopHeadlinesEntity

@Dao
interface NewsDao {
    @Insert
    suspend fun insertEverythingArticles(articleList: List<ArticleEntity>)

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
    suspend fun getAllEverythingArticles(): List<ArticleEntity>

    @Query("SELECT * FROM TOP_HEADLINES")
    suspend fun getAllTopHeadlines(): List<TopHeadlinesEntity>

    @Query("SELECT * FROM SOURCES")
    suspend fun getAllSources(): List<SourcesEntity>
}