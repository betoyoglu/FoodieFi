package com.example.foodiefi.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodiefi.data.model.Meal

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is FavoritesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF1E5128))
            }
        }
        is FavoritesUiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No favorites yet!", color = Color.Gray)
            }
        }
        is FavoritesUiState.Success -> {
            FavoritesContent(
                meals = state.meals,
                navController = navController
            )
        }
    }
}

@Composable
fun FavoritesContent(
    meals: List<Meal>,
    navController: NavController
) {
    val groupedMeals = meals.groupBy {
        it.mealCategory.ifEmpty { "Saved Items" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F6))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        groupedMeals.forEach { (category, mealList) ->
            CategorySection(
                categoryName = category,
                meals = mealList,
                navController = navController
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}