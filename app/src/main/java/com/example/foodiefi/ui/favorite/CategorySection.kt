package com.example.foodiefi.ui.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiefi.R
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.ui.components.RecipeFoodCard

@Composable
fun CategorySection(
    categoryName: String,
    meals: List<Meal>,
    navController: androidx.navigation.NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showAllItems by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = "arrow"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = categoryName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${meals.size}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular))
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Expand",
                modifier = Modifier.rotate(rotationState),
                tint = Color.Gray
            )
        }
        if (isExpanded) {
            Box(contentAlignment = Alignment.BottomCenter) {
                val displayCount = if (showAllItems) meals.size else meals.take(4).size
                val itemsToShow = meals.take(displayCount)

                Column {
                    itemsToShow.chunked(2).forEach { rowMeals ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            for (meal in rowMeals) {
                                Box(modifier = Modifier.weight(1f)) {
                                    RecipeFoodCard(
                                        foodImage = meal.imageUrl,
                                        foodName = meal.mealName,
                                        foodDescription = "",
                                        onClick = {
                                            navController.navigate("detailScreen/${meal.mealId}?time=&difficulty=&rating=")
                                        }
                                    )
                                }
                            }
                            if (rowMeals.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                if (meals.size > 4 && !showAllItems) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xFFFAF9F6).copy(alpha = 0.8f),
                                        Color(0xFFFAF9F6)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = { showAllItems = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E5128)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Text(text = "See All", color = Color.White)
                        }
                    }
                }
            }
        }

        HorizontalDivider(
            Modifier,
            DividerDefaults.Thickness,
            color = Color.LightGray.copy(alpha = 0.3f)
        )
    }
}