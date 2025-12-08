package com.example.foodiefi.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CategoryFilterChip(
    isSelected : Boolean, //state hoisting
    text: String,
    onClick: () -> Unit,// tıklama olayını üst sınıfa bildirmek için,
    chipImage: String,
){
        FilterChip(
            selected = isSelected,
            onClick = onClick,
            label = { Text(text) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color(0xFF2D6A4F),
                selectedLabelColor = Color.White,
                containerColor = Color.White,
                labelColor = Color.Black
            ),
            shape = CircleShape,
            leadingIcon = {
                AsyncImage(
                    model = chipImage,
                    contentDescription = "",
                    modifier = Modifier
                        .size(FilterChipDefaults.IconSize)
                        .clip(CircleShape)
                )
            }
        )
    }