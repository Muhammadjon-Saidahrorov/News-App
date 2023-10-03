package uz.gita.newsappmn.presentation.pages.sport

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
class SportViewModel @Inject constructor(
    private val diraction: Diraction,
    private val repastory: NewsRepastory
):SportContract.ViewModel,ViewModel() {
    override val container =
        container<SportContract.UIState, SportContract.SideEffect>(SportContract.UIState.Loading)

    override fun onEventDispatcher(intent: SportContract.Intent) {
        when (intent) {
            is SportContract.Intent.ClickItem -> {
                viewModelScope.launch {
                    diraction.navigateTo(ReadScreen(intent.articleData))
                }
            }
        }
    }

    init {
        repastory.loadAllNews("football","popularity").onEach {
            it.onSuccess {
                val list:List<ArticleData> = it.articles
                intent { reduce { SportContract.UIState.News(list) } }
            }
            it.onFailure{
                intent { postSideEffect(SportContract.SideEffect.Error(it.message!!)) }
            }
        }.launchIn(viewModelScope)
    }
}