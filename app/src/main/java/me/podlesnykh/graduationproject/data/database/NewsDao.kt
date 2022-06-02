package me.podlesnykh.graduationproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.podlesnykh.graduationproject.data.database.entities.EverythingEntity
import me.podlesnykh.graduationproject.data.database.entities.SourcesEntity
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlinesEntity
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel

@Dao
interface NewsDao {
    @Insert(entity = EverythingEntity::class)
    suspend fun insertEverything(everythingList: List<ArticleModel>)

    @Insert(entity = TopHeadlinesEntity::class)
    suspend fun insertTopHeadlines(topHeadlinesList: List<ArticleModel>)

    @Insert(entity = SourcesEntity::class)
    suspend fun insertSources(sourcesList: List<SourceModel>)

    @Query("DELETE FROM ARTICLES")
    suspend fun deleteEverything()

    @Query("DELETE FROM TOP_HEADLINES")
    suspend fun deleteTopHeadlines()

    @Query("DELETE FROM SOURCES")
    suspend fun deleteSources()

    @Query("SELECT * FROM ARTICLES")
    suspend fun getAllEverything(): List<EverythingEntity>

    @Query("SELECT * FROM TOP_HEADLINES")
    suspend fun getAllTopHeadlines(): List<TopHeadlinesEntity>

    @Query("SELECT * FROM SOURCES")
    suspend fun getAllSources(): List<SourcesEntity>
}