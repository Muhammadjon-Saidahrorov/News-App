package uz.gita.newsappmn.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import uz.gita.newsappmn.R

@Composable
fun ArticleItem(hour: String, day: String, title: String, url: String?, click: () -> Unit) {
    val painter: AsyncImagePainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )
    var logic = url != null


    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clickable { click.invoke() }

    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .width(120.dp)
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = if(logic) painter else painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            Modifier
                .weight(1f)
        ) {
            Row() {
                Image(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,

                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(Color.LightGray),
                )

                Text(text = "$hour / ", color = Color.LightGray, fontSize = 12.sp)
                Text(text = "$day", color = Color.LightGray,fontSize = 12.sp, textAlign = TextAlign.Justify)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(Modifier.weight(1f)) {
                Text(text = title, Modifier.padding(end = 4.dp, bottom = 4.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 0.2.sp)
            }


        }
    }
}

@Preview
@Composable
fun ArticlePrev() {
    ArticleItem(
        "17:00",
        "21.06.2023",
        "How do you prevent an AI-generated game from losing the plot?",
        "https://s.yimg.com/uu/api/res/1.2/.hq4M8ynGA75qSMDXfMAvQ--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-06/85b65bf0-0f82-11ee-bb9f-a9b5afa07646.cf.jpg",
        {})
}