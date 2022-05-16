package me.podlesnykh.graduationproject.network

import me.podlesnykh.graduationproject.network.models.ArticlesResponse
import me.podlesnykh.graduationproject.network.models.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") keyword: String,
        @Query("searchIn") searchIn: String,
        @Query("sources") sources: String,
        @Query("domains") domains: String,
        @Query("excludeDomains") excludeDomains: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String
    ) : ArticlesResponse

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        // DO NOT MIX WITH THE SOURCES
        @Query("country") country: String,
        // DO NOT MIX WITH THE SOURCES
        @Query("category") category: String,
        // DO NOT MIX WITH THE COUNTRY & CATEGORY
        @Query("sources") sources: String,
        @Query("q") q: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String
    ) : ArticlesResponse

    @GET("/v2/top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("country") country: String
    ) : SourcesResponse
}