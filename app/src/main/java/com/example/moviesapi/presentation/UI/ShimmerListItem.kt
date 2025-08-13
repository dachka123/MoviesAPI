package com.example.moviesapi.presentation.UI

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloat

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.example.moviesapi.core.Dimens

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if(isLoading){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFF121212))
                .padding(Dimens.spacing16)
        ) {
            Box(
                modifier = modifier
                    .height(Dimens.spacing144)
                    .width(Dimens.spacing107)
                    .clip(RoundedCornerShape(Dimens.spacing16))
                    .shimmerEffect(),
                )
            Spacer(modifier = Modifier.width(Dimens.spacing13))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Box(
                    modifier = modifier
                        .height(Dimens.spacing18)
                        .width(Dimens.spacing109)
                        .clip(RoundedCornerShape(Dimens.spacing16))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(Dimens.spacing13))

                Box(
                    modifier = modifier
                        .height(Dimens.spacing10)
                        .width(Dimens.spacing195)
                        .clip(RoundedCornerShape(Dimens.spacing16))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(Dimens.spacing5))

                Box(
                    modifier = modifier
                        .height(Dimens.spacing10)
                        .width(Dimens.spacing98)
                        .clip(RoundedCornerShape(Dimens.spacing16))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(Dimens.spacing5))

                Box(
                    modifier = modifier
                        .height(Dimens.spacing10)
                        .width(Dimens.spacing158)
                        .clip(RoundedCornerShape(Dimens.spacing16))
                        .shimmerEffect()
                )
            }
        }
    }

}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetx by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ),
            start = Offset(startOffsetx,0f),
            end = Offset(startOffsetx + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned({
            size = it.size
        })
}

@Preview
@Composable
private fun ShimmerListItemPrev() {
    ShimmerListItem(
        isLoading = true,
    )
}