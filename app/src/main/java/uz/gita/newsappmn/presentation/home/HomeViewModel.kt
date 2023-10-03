package uz.gita.newsappmn.presentation.home

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
import uz.gita.newsappmn.domain.NewsRepastory
import uz.gita.newsappmn.navigation.AppNavigator
import uz.gita.newsappmn.presentation.read.ReadScreen
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repastory: NewsRepastory,
    private val appNavigator: AppNavigator
) : ViewModel(), HomeContract.ViewModel {

    override val container =
        container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.SearchData -> {
                intent { reduce { state.copy(loading = true) } }
                repastory.loadAllNews(intent.query, "popularity").onEach {
                    it.onSuccess {
                        intent { reduce { state.copy(loading = false, news = it.articles) } }
                    }
                    it.onFailure {
                        intent { postSideEffect(HomeContract.SideEffect.Toast(it.message!!)) }
                    }
                }.launchIn(viewModelScope)
            }
            is HomeContract.Intent.Navigate -> {
                viewModelScope.launch {
                    appNavigator.navigateTo(ReadScreen(intent.articleData))
                }
            }
        }
    }
}