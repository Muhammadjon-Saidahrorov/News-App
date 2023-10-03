package uz.gita.newsappmn.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.newsappmn.R
import uz.gita.newsappmn.navigation.AppScreen
import uz.gita.newsappmn.presentation.pages.all.AllPage
import uz.gita.newsappmn.presentation.pages.media.MediaPage
import uz.gita.newsappmn.presentation.pages.sport.SportPage
import uz.gita.newsappmn.ui.component.ArticleItem
import uz.gita.newsappmn.util.getDate
import uz.gita.newsappmn.util.getHour

class HomeScreen : AppScreen() {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val uiState = viewModel.collectAsState()
        TopBarContent(uiState, viewModel::onEventDispatcher)

        viewModel.collectSideEffect {
            when (it) {
                is HomeContract.SideEffect.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(
    uiState: State<HomeContract.UIState>,
    eventDispatcher: (HomeContract.Intent) -> Unit
) {

    var searchText by remember { mutableStateOf("") }
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F2038))
                .height(80.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            TextField(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 26.dp),
                value = searchText,
                onValueChange = {
                    if (it.length > 3)
                        eventDispatcher.invoke(
                            HomeContract.Intent.SearchData(it)
                        )
                    searchText = it
                },
                placeholder = {
                    Text(
                        text = "Search",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = colorResource(id = R.color.white),
                    cursorColor = Color.Red
                ),
                singleLine = false,
                shape = RoundedCornerShape(14.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear", tint = Color.LightGray
                            )
                        }
                    }
                })
            Spacer(modifier = Modifier.width(8.dp))

        }
        if (searchText.length < 3) {
            TabNavigator(AllPage) {
                Column() {
                    Scaffold(
                        bottomBar = {
                            Column() {

                                NavigationBar(
                                    Modifier
                                        .height(60.dp)
                                        .background(Color(0xFF0F2038)), Color(0xFF0F2038)
                                ) {
                                    TabNavigationItem(tab = AllPage)
                                    TabNavigationItem(tab = SportPage)
                                }
                            }
                        },
                        content = {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(it)
                            ) {
                                CurrentTab()
                            }
                        }
                    )
                }

            }
        } else {
            if (uiState.value.loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(uiState.value.news) { article ->
                        ArticleItem(
                            hour = getHour(article.publishedAt),
                            day = getDate(article.publishedAt),
                            title = article.title,
                            url = article.urlToImage,
                            click = {
                                eventDispatcher.invoke(HomeContract.Intent.Navigate(article))
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current



    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { },
        label = {
            Text(
                text = tab.options.title,
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White,
                fontSize = 16.sp
            )
        },
        colors = NavigationBarItemDefaults.colors(Color.Yellow)
    )
}

