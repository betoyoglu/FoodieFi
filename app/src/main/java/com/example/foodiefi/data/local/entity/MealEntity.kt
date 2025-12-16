package com.example.foodiefi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class MealEntity (
    @PrimaryKey
    val mealId: String,
    val mealName: String,
    val mealImageUrl: String,
    val mealCategory: String
)