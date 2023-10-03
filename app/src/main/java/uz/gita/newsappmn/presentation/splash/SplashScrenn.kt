package uz.gita.newsappmn.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import uz.gita.newsappmn.navigation.AppScreen

class SplashScrenn() : AppScreen() {

    private val scope = CoroutineScope(Dispatchers.Main)

    @Composable
    override fun Content() {

        val viewModel = getViewModel<SplashViewModel>()

        Box(modifier = Modifier.background(Color(0xFFBB1819)),
            contentAlignment = Alignment.Center) {
            Text(
                text = "My News",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )
        }
    LaunchedEffect(scope){
        delay(2000)
        viewModel.navigate()
    }
    }

}