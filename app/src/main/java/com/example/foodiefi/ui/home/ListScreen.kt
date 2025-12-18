package com.example.foodiefi.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodiefi.R
import com.example.foodiefi.ui.components.CategoryFilterChip
import com.example.foodiefi.ui.components.RecipeFoodCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    listType: String, // "categories" veya "meals" gelecek
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val recommendations by viewModel.recommendations.collectAsStateWithLifecycle()

    val title = if (listType == "categories") "All Categories" else "All Recipes"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.googlesans_regular)), color = Color(0xFF1E5128))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1E5128)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAF9F6))
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF9F6))
                .padding(paddingValues)
        ) {
            if (listType == "categories") {
                // --- TÜM KATEGORİLER ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { category ->
                        CategoryFilterChip(
                            isSelected = false,
                            text = category.categoryName,
                            onClick = {
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("selected_category_result", category.categoryName)

                                navController.popBackStack()
                            },
                            chipImage = category.categoryImageUrl
                        )
                    }
                }
            } else {
                // --- TÜM YEMEKLER ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recommendations) { meal ->
                        RecipeFoodCard(
                            foodImage = meal.mealImageUrl,
                            foodName = meal.mealName,
                            foodDescription = meal.mealDescription,
                            onClick = {
                                navController.navigate(
                                    "detailScreen/${meal.mealId}?time=${meal.time}&difficulty=${meal.difficulty}&rating=${meal.rating}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}