package com.example.foodiefi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodiefi.data.local.entity.MealEntity

@Database(entities = [MealEntity::class], version = 2)
abstract class FoodDatabase : RoomDatabase(){
    abstract val mealDao : MealDao
}