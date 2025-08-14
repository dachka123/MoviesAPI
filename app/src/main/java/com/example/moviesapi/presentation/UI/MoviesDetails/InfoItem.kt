package com.example.moviesapi.presentation.UI.MoviesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    firstText: String,
    secondText: Int,
    iconTint: Color = Color.Yellow,
    primaryTextColor: Color = Color.LightGray,
    secondaryTextColor: Color = Color.White
) {
    InfoItemContent(
        modifier = modifier,
        icon = icon,
        firstText = firstText,
        secondText = secondText.toString(),
        iconTint = iconTint,
        primaryTextColor = primaryTextColor,
        secondaryTextColor = secondaryTextColor
    )
}

@Composable
fun InfoItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    firstText: String,
    secondText: Double,
    iconTint: Color = Color.Yellow,
    primaryTextColor: Color = Color.LightGray,
    secondaryTextColor: Color = Color.White
) {
    InfoItemContent(
        modifier = modifier,
        icon = icon,
        firstText = firstText,
        secondText = secondText.toString(),
        iconTint = iconTint,
        primaryTextColor = primaryTextColor,
        secondaryTextColor = secondaryTextColor
    )
}

@Composable
private fun InfoItemContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    firstText: String,
    secondText: String,
    iconTint: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color
) {
    Box(
        modifier = modifier
            .height(135.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(20.dp),
                color = Color.Gray
            )
            .background(
                Color(0xFF121212),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconTint
            )

            Text(
                text = firstText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = secondText,
                fontSize = 24.sp,
                color = secondaryTextColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}