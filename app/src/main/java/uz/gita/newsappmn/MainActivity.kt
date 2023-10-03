package uz.gita.newsappmn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsappmn.navigation.NavigationHandler
import uz.gita.newsappmn.presentation.home.HomeScreen
import uz.gita.newsappmn.presentation.splash.SplashScrenn
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.news_color)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Navigator(screen = HomeScreen()) { nav ->

                    LaunchedEffect(nav) {
                        navigationHandler.navigationStack.onEach {
                            it.invoke(nav)
                        }.launchIn(lifecycleScope)
                    }
                    CurrentScreen()
                }
            }
        }
    }
}
