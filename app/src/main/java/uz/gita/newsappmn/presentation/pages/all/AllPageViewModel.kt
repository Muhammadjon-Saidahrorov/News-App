package uz.gita.newsappmn.presentation.pages.all

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
import uz.gita.newsappmn.util.logger
import javax.inject.Inject

@HiltViewModel
class AllPageViewModel @Inject constructor(
    private val repastory: NewsRepastory,
    private val diraction: Diraction
) : AllPageContract.ViewModel, ViewModel() {

    override val container =
        container<AllPageContract.UIState, AllPageContract.SideEffect>(AllPageContract.UIState.Loading)

    override fun onEventDispatcher(intent: AllPageContract.Intent) {
        when (intent) {
            is AllPageContract.Intent.ClickItem -> {
                viewModelScope.launch {
                    logger("navigate")
                    diraction.navigateTo(ReadScreen(intent.articleData))
                }
            }
        }
    }

    init {
        repastory.loadAllNews("bbc-news", "popularity").onEach {
            it.onSuccess {
                val list: List<ArticleData> = it.articles
                intent { reduce { AllPageContract.UIState.News(list) } }
            }
            it.onFailure {
                intent { postSideEffect(AllPageContract.SideEffect.Error(it.message!!)) }
            }
        }.launchIn(viewModelScope)
    }

}