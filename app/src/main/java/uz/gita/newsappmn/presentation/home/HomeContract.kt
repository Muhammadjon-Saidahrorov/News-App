package uz.gita.newsappmn.presentation.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsappmn.data.model.ArticleData

interface HomeContract {
    sealed interface ViewModel : ContainerHost<UIState,SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
    sealed interface SideEffect {
        data class Toast(val message:String):SideEffect
    }
    data class UIState(
        val loading:Boolean = false,
        val news: List<ArticleData> = emptyList()
    )
    sealed interface Intent {
        data class SearchData(val query:String):Intent
        data class Navigate(val articleData: ArticleData):Intent
    }
}