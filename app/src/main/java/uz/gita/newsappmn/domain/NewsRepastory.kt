package uz.gita.newsappmn.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.newsappmn.data.source.remote.response.NewsResponse

interface NewsRepastory {
    fun loadAllNews( tema:String, sortBy:String): Flow<Result<NewsResponse>>
    fun loadNewsByTheme(tema: String): Flow<Result<NewsResponse>>
    fun loadMediaNews(tema: String): Flow<Result<NewsResponse>>
}