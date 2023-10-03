package uz.gita.newsappmn.presentation.pages.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.newsappmn.data.model.ArticleData
import uz.gita.newsappmn.domain.NewsRepastory
import uz.gita.newsappmn.presentation.pages.diraction.Diraction
import uz.gita.newsappmn.presentation.read.ReadScreen
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val diraction: Diraction,
    private val repastory: NewsRepastory
) : MediaContract.ViewModel, ViewModel() {
    override val container =
        container<MediaContract.UIState, MediaContract.SideEffect>(MediaContract.UIState.Loading)

    override fun onEventDispatcher(intent: MediaContract.Intent) {
        when (intent) {
            is MediaContract.Intent.ClickItem -> {
                viewModelScope.launch {
                    diraction.navigateTo(ReadScreen(intent.articleData))
                }
            }
        }
    }

    init {
        repastory.loadAllNews("hollywood","popularity").onEach {
            it.onSuccess {
                val list:List<ArticleData> = it.articles
                intent { reduce { MediaContract.UIState.News(list) } }
            }
            it.onFailure{
                intent { postSideEffect(MediaContract.SideEffect.Error(it.message!!)) }
            }
        }.launchIn(viewModelScope)
    }

}