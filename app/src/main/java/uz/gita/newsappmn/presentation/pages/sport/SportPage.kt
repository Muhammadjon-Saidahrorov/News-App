package uz.gita.newsappmn.presentation.pages.sport

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.newsappmn.ui.component.ArticleItem
import uz.gita.newsappmn.util.logger

object SportPage : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Sport"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = null
                )
            }
        }

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SportContract.ViewModel = getViewModel<SportViewModel>()
        val uiState = viewModel.collectAsState().value

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is SportContract.SideEffect.Error -> {
                    logger(sideEffect.message)
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        SportPageconten(uiState, viewModel::onEventDispatcher)
    }
}

@Composable
fun SportPageconten(
    uiState: SportContract.UIState,
    eventDispatcher: (SportContract.Intent) -> Unit
) {
    var mUrl = ""
    when (uiState) {
        SportContract.UIState.Loading -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        }
        is SportContract.UIState.News -> {
            Column() {
                LazyColumn() {
                    items(uiState.articles) { article ->
                        ArticleItem(
                            hour = getHour(article.publishedAt),
                            day = getDate(article.publishedAt),
                            title = article.title,
                            url = article.urlToImage,
                            click = {
                                logger("click")
                                eventDispatcher.invoke(
                                    SportContract.Intent.ClickItem(article)
                                )
                            })
                    }
                }
            }

        }
    }
}


private fun getHour(time: String): String {
    return time.substring(11, 16)
}

private fun getDate(time: String): String {
    return time.substring(0, 10)
}