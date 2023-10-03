package uz.gita.newsappmn.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.newsappmn.data.source.remote.response.NewsResponse

interface NewsApi {

    @GET("/v2/everything")
    suspend fun getAllNews(@Query("apiKey") key: String, @Query("from") from:String, @Query("q") tema:String, @Query("sortBy") sortBy:String): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun getNewsByQuery()

    @GET("/v2/top-headlines")
    suspend fun getTopNews(@Query("apiKey") key: String, @Query("sources") sources:String): Response<NewsResponse>
    //"https://newsapi.org/v2/everything?q=$query&apiKey=$apiKey"

    @GET("/v2/top-headlines")
    suspend fun getCountryNews(@Query("apiKey") key: String, @Query("country") country:String): Response<NewsResponse>
}