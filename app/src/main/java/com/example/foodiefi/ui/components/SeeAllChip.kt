package com.example.foodiefi.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.foodiefi.R

@Composable
fun SeeAllChip(
    onClick: () -> Unit,
){
    FilterChip(
        selected = true,
        onClick = onClick,
        label = {
            Text(
                text = "See All",
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF1E5128),
            selectedLabelColor = Color.White,
            containerColor = Color.White,
            labelColor = Color.Black
        ),
        shape = CircleShape,
    )

}