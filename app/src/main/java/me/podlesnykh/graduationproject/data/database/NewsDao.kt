package me.podlesnykh.graduationproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.podlesnykh.graduationproject.data.database.entities.Everything
import me.podlesnykh.graduationproject.data.database.entities.Sources
import me.podlesnykh.graduationproject.data.database.entities.TopHeadlines
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.SourceModel

@Dao
interface NewsDao {
    @Insert(entity = Everything::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverything(everythingList: List<ArticleModel>)

    @Insert(entity = TopHeadlines::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlines(topHeadlinesList: List<ArticleModel>)

    @Insert(entity = Sources::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sourcesList: List<SourceModel>)

    @Query("DELETE FROM everything")
    suspend fun deleteEverything()

    @Query("DELETE FROM topheadlines")
    suspend fun deleteTopHeadlines()

    @Query("DELETE FROM sources")
    suspend fun deleteSources()

    @Query("SELECT * FROM everything")
    suspend fun getAllEverything(): List<Everything>

    @Query("SELECT * FROM topheadlines")
    suspend fun getAllTopHeadlines(): List<TopHeadlines>

    @Query("SELECT * FROM sources")
    suspend fun getAllSources(): List<Sources>
}