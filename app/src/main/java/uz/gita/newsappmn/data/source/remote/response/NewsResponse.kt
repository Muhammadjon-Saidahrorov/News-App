package uz.gita.newsappmn.data.source.remote.response

import uz.gita.newsappmn.data.model.ArticleData

data class NewsResponse(
    val articles: List<ArticleData>,
    val status: String,
    val totalResults: Int
)