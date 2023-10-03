package uz.gita.newsappmn.presentation.read

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import uz.gita.newsappmn.R
import uz.gita.newsappmn.data.model.ArticleData
import uz.gita.newsappmn.navigation.AppScreen

class ReadScreen(val articleData: ArticleData) : AppScreen() {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<ReadScreenViewModel>()
        WebContent(data = articleData, viewModel)
    }
}

@Composable
fun WebContent(data: ArticleData, viewModel: ReadScreenViewModel) {

    val painter: AsyncImagePainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = data.urlToImage)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F2038))
                .height(56.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.back() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painter, contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 8.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.title, color = Color.Black, fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.description, textAlign = TextAlign.Justify, fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp),
        )
    }


}