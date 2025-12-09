package com.example.foodiefi.ui

data class MealUiState(
    val mealId: String,
    val mealName: String,
    val mealImageUrl: String,
    val mealDescription: String = "",
    val time: String,
    val difficulty: String,
    val rating: String
)
