package com.example.moviesapi.presentation.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain

@Composable
fun MovieCard(
    movie: MoviesDomain,
    modifier: Modifier = Modifier,
    onClick: (MoviesDomain) -> Unit

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF121212))
            .clickable { onClick(movie) }
            .padding(Dimens.spacing16)
    ) {
        Box {
            if (!movie.poster.isNullOrEmpty()) {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = movie.poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(Dimens.spacing144)
                        .width(Dimens.spacing107)
                        .clip(RoundedCornerShape(Dimens.spacing16)),
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_gallery)
                )
            }

            if (movie.isFavorite) {
                Box(
                    modifier = Modifier
                        .size(Dimens.spacing19)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-7).dp, y = (-7).dp)
                        .background(
                            color = Color.Black,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.btn_star_big_on),
                        contentDescription = "Favorite",
                        tint = Color.Yellow,
                        modifier = Modifier.size(Dimens.spacing13)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Dimens.spacing14))

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = movie.description,
                color = Color.White,
                fontSize = 13.sp,
                letterSpacing = 0.sp
            )
        }
    }
}