package com.example.foodiefi.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.foodiefi.R
import com.example.foodiefi.ui.components.CategoryFilterChip
import com.example.foodiefi.ui.components.FoodCard
import com.example.foodiefi.ui.components.RecipeFoodCard
import com.example.foodiefi.ui.components.SearchBarM3

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    //viewmodeldeki stateleri dinle (collect)
    val categoryList by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val queryText by viewModel.queryText.collectAsStateWithLifecycle()
    val isActive by viewModel.isActive.collectAsStateWithLifecycle()
    val recommendations by viewModel.recommendations.collectAsStateWithLifecycle()

    val gradientColorList = listOf(
        Color(0xFFD9E9CF),
        Color(0xFFD3D3D3),
        Color(0xFFFAF9F6),
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColorList))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ){
        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        ){
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
                modifier = Modifier.align (Alignment.CenterVertically)
            )
        }
        Text(
            text = "Feeling Hungry? \nWhat are we cookin' today?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.googlesans_semibold)),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        )
        SearchBarM3(
            query = queryText,
            onQueryChange = {queryText ->
                viewModel.onQueryChange(queryText)
            },
            onSearch = {query ->
                viewModel.onSearch(query)
            },
            active = isActive,
            onActiveChange = {newActive ->
                viewModel.onActiveChange(newActive)
            }
        )

        //category chips
        LazyRow (
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ){
            items(categoryList) {category ->
                CategoryFilterChip(
                    isSelected = (category.categoryName == selectedCategory),
                    text = category.categoryName,
                    onClick = {
                        viewModel.onCategorySelected(category.categoryName)
                    },
                    chipImage = category.categoryImageUrl
                )
            }
        }

        //recommendation
        Text(
            text = "Recommendation",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.googlesans_regular)),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
        )

        LazyRow (
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
           items(items= recommendations, key = {meal -> meal.mealId}){ meal ->
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
        }

        //recipe of the week
        Text(
            text = "Recipe of The Week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.googlesans_regular)),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
        )

        LazyRow (
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(items= recommendations, key = {meal -> meal.mealId}){ meal ->
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