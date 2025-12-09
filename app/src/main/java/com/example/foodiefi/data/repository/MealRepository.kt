package com.example.foodiefi.data.repository

import com.example.foodiefi.data.api.FoodApi
import com.example.foodiefi.data.model.Category
import com.example.foodiefi.data.model.Meal
import javax.inject.Inject

//veriyi apiden alıp suncaz

class MealRepository @Inject constructor(private val api : FoodApi) {
    suspend fun getMeals(category:String) : List<Meal>{
        return api.getMeals(category).meals
    }

    suspend fun getCategories() : List<Category>{
        return api.getCategories().categories
    }
}