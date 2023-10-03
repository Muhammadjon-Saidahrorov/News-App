package uz.gita.newsappmn.presentation.pages.media

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsappmn.data.model.ArticleData

interface MediaContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState{
        object Loading:UIState
        data class News(val articles:List<ArticleData>):UIState
    }
    sealed interface SideEffect{
        data class Error(val message:String):SideEffect
    }
    sealed interface Intent{
        data class ClickItem(val articleData: ArticleData):Intent
    }
}