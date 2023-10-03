package uz.gita.newsappmn.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import uz.gita.newsappmn.data.source.remote.api.NewsApi
import uz.gita.newsappmn.data.source.remote.response.NewsResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRepastoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepastory {
    private val key = "534cf69953e64b93b91f37233ef1d6d7"

    override fun loadAllNews(tema: String, sortBy: String): Flow<Result<NewsResponse>> =
        flow<Result<NewsResponse>> {
            val response = api.getAllNews(key,getDate(),tema,sortBy)
            if(response.isSuccessful){
                val body = response.body() as NewsResponse
                emit(Result.success(body))
            }else{
                val error:String = response.errorBody()?.string()!!
                emit(Result.failure(Exception(error)))
            }

        }.catch { emit(Result.failure(it)) }
            .flowOn(Dispatchers.IO)


    override fun loadNewsByTheme(tema: String): Flow<Result<NewsResponse>> = callbackFlow{

        val response = api.getTopNews(key,tema)

        if(response.isSuccessful){
            val body = response.body() as NewsResponse
            trySend(Result.success(body))
        }else{
            val error:String = response.errorBody()?.string()!!
            trySend(Result.failure(Exception(error)))
        }

        awaitClose()
    }

    override fun loadMediaNews(tema: String): Flow<Result<NewsResponse>> = callbackFlow{
        val response = api.getCountryNews(key, tema)
        if(response.isSuccessful){
            val body = response.body() as NewsResponse
            trySend(Result.success(body))
        }else{
            val error:String = response.errorBody()?.string()!!
            trySend(Result.failure(Exception(error)))
        }

        awaitClose()
    }

    fun getDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -3)
        val threeDayAgo = calendar.time
        return dateFormat.format(threeDayAgo)
    }
}