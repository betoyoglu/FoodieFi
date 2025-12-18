package com.example.foodiefi.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodiefi.R
import com.example.foodiefi.ui.components.Carousel
import com.example.foodiefi.ui.components.CategoryFilterChip
import com.example.foodiefi.ui.components.FoodCard
import com.example.foodiefi.ui.components.RecipeFoodCard
import com.example.foodiefi.ui.components.SearchBarM3
import com.example.foodiefi.ui.components.SeeAllCard
import com.example.foodiefi.ui.components.SeeAllChip

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val categoryList by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val queryText by viewModel.queryText.collectAsStateWithLifecycle()
    val isActive by viewModel.isActive.collectAsStateWithLifecycle()
    val recommendations by viewModel.recommendations.collectAsStateWithLifecycle()
    val recipesOfTheWeek by viewModel.recipesOfTheWeek.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    val currentBackStackEntry = navController.currentBackStackEntry
    val selectedCategoryResult by currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>("selected_category_result", null)
        ?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }

    LaunchedEffect(selectedCategoryResult) {
        selectedCategoryResult?.let { categoryName ->
            viewModel.onCategorySelected(categoryName)
            currentBackStackEntry?.savedStateHandle?.remove<String>("selected_category_result")
        }
    }


    val gradientColorList = listOf(
        Color(0xFFD9E9CF),
        Color(0xFFD3D3D3),
        Color(0xFFFAF9F6),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColorList))
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.watermelon),
                contentDescription = "watermelon",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Welcome to FoodieFi",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = "Feeling Hungry? \nWhat are we cookin' today?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.googlesans_semibold)),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        SearchBarM3(
            query = queryText,
            onQueryChange = { viewModel.onQueryChange(it) },
            onSearch = { viewModel.onSearch(it) },
            active = isActive,
            onActiveChange = { viewModel.onActiveChange(it) },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (queryText.isNotEmpty()) {
            if (searchResults.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No recipes found for '$queryText'",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults) { meal ->
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
        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Carousel()

                //category chips
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(categoryList.take(5)) { category ->
                        CategoryFilterChip(
                            isSelected = (category.categoryName == selectedCategory),
                            text = category.categoryName,
                            onClick = { viewModel.onCategorySelected(category.categoryName) },
                            chipImage = category.categoryImageUrl
                        )
                    }
                    item {
                        SeeAllChip(
                            onClick = {navController.navigate("listScreen/categories")}
                        )
                    }
                }

                //recommendation
                Text(
                    text = "Recommendation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = recommendations.take(5), key = { meal -> meal.mealId }) { meal ->
                        FoodCard(
                            foodImage = meal.mealImageUrl,
                            foodName = meal.mealName,
                            foodDescription = meal.mealDescription,
                            foodTime = meal.time,
                            onClick = {
                                navController.navigate(
                                    "detailScreen/${meal.mealId}?time=${meal.time}&difficulty=${meal.difficulty}&rating=${meal.rating}"
                                )
                            }
                        )
                    }
                    item {
                        SeeAllCard (
                            modifier = Modifier
                                .width(200.dp)
                                .height(260.dp),
                            onClick = {navController.navigate("listScreen/meals")}
                        )
                    }
                }

               //recipe of the week
                Text(
                    text = "Recipe of The Week",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = recipesOfTheWeek.take(5), key = { meal -> meal.mealId }) { meal ->
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
                    item {
                        SeeAllCard (
                            modifier = Modifier
                                .width(200.dp)
                                .height(260.dp),
                            onClick = {navController.navigate("listScreen/meals")}
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}