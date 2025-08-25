package com.example.moviesapi.presentation.UI.MoviesDetails

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.presentation.MoviesViewModel

@Composable
fun MoviesDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    onBack: () -> Unit,
    viewModel: MoviesViewModel
) {
    val movie = viewModel.state.movies.find { it.id == movieId }

    if (movie == null) {
        Text("Movie not found")
        return
    }
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .statusBarsPadding()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp, vertical = Dimens.spacing8),
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 60.dp)
            )

            IconButton(
                onClick = {
                    viewModel.toggleFavorite(movie.id, movie.isFavorite)
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_star_big_on),
                    contentDescription = "Favorite",
                    tint = if (movie.isFavorite) Color.Yellow else Color.White,
                    modifier = Modifier.size(Dimens.spacing20)
                )
            }
        }
        Box {
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(272.dp),
                placeholder = painterResource(id = R.drawable.ic_menu_gallery),
                error = painterResource(id = R.drawable.ic_menu_gallery)
            )

            //top blur
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF121212),
                                Color.Transparent
                            )
                        )
                    )
            )

            // bottom blur
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF121212)
                            )
                        )
                    )
            )
            LazyRow(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movie.genre) { genre ->
                    GenreItem(
                        genre = genre
                    )
                }
            }
        }
        Spacer(modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(34.dp)
        ) {
            InfoItem(
                modifier = modifier.weight(1f),
                icon = Icons.Default.CameraAlt,
                firstText = "Year",
                secondText = movie.year,
                iconTint = Color.Yellow,
                primaryTextColor = Color.LightGray,
                secondaryTextColor = Color.White
            )
            InfoItem(
                modifier = modifier.weight(1f),
                icon = Icons.Default.Star,
                firstText = "Rating",
                secondText = movie.rating,
                iconTint = Color.Yellow,
                primaryTextColor = Color.LightGray,
                secondaryTextColor = Color.White
            )
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ){
            Text(
                modifier = Modifier,
                text = movie.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = movie.description,
                fontSize = 13.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = {
                    Toast.makeText(context,"${movie.title} added to watchlist", Toast.LENGTH_SHORT).show()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(10.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow
                )
            ) {
                Text(
                    modifier = Modifier,
                    text = "Add to watchlist",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}