package com.example.foodiefi.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.foodiefi.R
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.ui.theme.FoodieFiTheme
import androidx.core.net.toUri

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailViewModel = hiltViewModel())
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState){
        is DetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()
            , contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = Color(0xFF1E5128))
            }
        }
        is DetailUiState.Success -> {
            DetailContent(
                meal = state.meal,
                time = state.time,
                difficulty = state.difficulty,
                rating = state.rating,
                isFavorite = state.isFavorite,
                onFavoriteClick= {viewModel.onFavoriteClick()},
                navController = navController
            )
        }
        is DetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center){
                Text(text = "Error")
            }
        }
    }
}

@Composable
fun DetailContent(
    meal: Meal,
    time: String,
    difficulty: String,
    rating: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    var isExpanded by remember {mutableStateOf(false)}


    var isCategoryExpanded by remember { mutableStateOf(false) }
    val categoryRotationState by animateFloatAsState(
        targetValue = if(isCategoryExpanded) 180f else 0f,
        label = "ArrowRotation"
    )

    var isIngredientsExpanded by remember { mutableStateOf(false) }
    val ingredientRotationState by animateFloatAsState(
        targetValue = if(isIngredientsExpanded) 180f else 0f,
        label = "ArrowRotation"
    )

    val ingredients = meal.getIngredientsWithMeasures()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F6))
            .padding(bottom = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = meal.mealName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(R.drawable.timer),
                        contentDescription = "timer",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = time,
                        fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(R.drawable.chart),
                        contentDescription = "difficulty",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = difficulty,
                        fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                        fontSize = 14.sp
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = rating,
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(400.dp, 350.dp)
        ) {
            AsyncImage(
                model = meal.imageUrl,
                placeholder = painterResource(R.drawable.watermelon),
                contentDescription = meal.mealName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(50))
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .animateContentSize()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .clickable{isCategoryExpanded =! isCategoryExpanded}
            ){
                Text(
                    text = "Category & Area",
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                IconButton(
                    onClick = {isCategoryExpanded =! isCategoryExpanded}
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "arrow",
                        modifier = Modifier.rotate(categoryRotationState)
                    )
                }
            }
            AnimatedVisibility(visible = isCategoryExpanded) {
                Column (modifier = Modifier.padding(top = 8.dp)){
                    Text(
                        text = "Category: ${meal.mealCategory}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.googlesans_regular))
                    )
                    Text(
                        text = "Area: ${meal.mealArea}",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .clickable{isIngredientsExpanded =! isIngredientsExpanded}
            ){
                Text(
                    text = "Ingredients",
                    fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                IconButton(
                    onClick = {isIngredientsExpanded =! isIngredientsExpanded}
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "arrow",
                        modifier = Modifier.rotate(ingredientRotationState)
                    )
                }
            }
            AnimatedVisibility(visible = isIngredientsExpanded) {
                Column (modifier = Modifier.padding(top = 8.dp)){
                    if(ingredients.isNotEmpty()){
                        ingredients.forEach { item->
                            IngredientRow(ingredient =  item.first, measure = item.second)
                        }
                    }
                    else {
                        Text(text= "No ingredients information found.", color= Color.Gray)
                    }
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .animateContentSize()
        ) {
            Text(
                text = "Directions",
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = meal.mealInstructions,
                style= MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable{isExpanded =! isExpanded}
            )
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                color = Color(0xFF1E5128),
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(top = 4.dp)
            )

        }

        Spacer(modifier = Modifier.height(24.dp))
        
        if (meal.mealYoutubeLink.isNotEmpty()){
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, meal.mealYoutubeLink.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E5128),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Watch Video",
                    fontFamily = FontFamily(Font(R.font.googlesans_semibold)),
                )
            }
        }
    }

}

@Composable
fun IngredientRow(ingredient: String, measure: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color(0xFF1E5128), RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = ingredient,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.googlesans_regular))
            )
        }
        Text(
            text = measure,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.googlesans_regular))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val mockMeal = Meal(
        mealId = "12345",
        mealName = "Spicy Meatball & Salad",
        imageUrl = "",
        mealCategory = "Main Course",
        mealArea = "American",
        mealInstructions ="ujhnewfdkolskdoskcoşfkclsockö wefıkjmwklf wefjmwıjwı efkjwoofkwo wkefjoıfkwokf \n sefjwıojelşe",
        mealYoutubeLink = "ujhn",
        strIngredient1 = "Meatballs",
        strIngredient2 = "Lettuce",
        strIngredient3 = "Tomato",
        strIngredient4 = "Onion",
        strIngredient5 = "Cheese",
        strMeasure1 = "1 lb",
        strMeasure2 = "1 lb",
        strMeasure3 = "1 lb",
        strMeasure4 = "1 lb",
        strMeasure5 = "1 lb"
    )

    // 2. Teman ile sarmalayıp içeriği çağırıyoruz
    FoodieFiTheme {
        DetailContent(
            meal = mockMeal,
            time = "35 mins",
            difficulty = "Medium",
            rating = "4.8",
            isFavorite = false,
            onFavoriteClick = {},
            navController = rememberNavController() // Sahte navigasyon kumandası
        )
    }
}