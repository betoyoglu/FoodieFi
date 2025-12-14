package com.example.foodiefi.data.repository

import com.example.foodiefi.data.api.FoodApi
import com.example.foodiefi.data.local.MealDao
import com.example.foodiefi.data.local.entity.MealEntity
import com.example.foodiefi.data.model.Category
import com.example.foodiefi.data.model.Meal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//veriyi apiden alıp suncaz

class MealRepository @Inject constructor(
    private val api : FoodApi,
    private val mealDao: MealDao
) {
    suspend fun getMeals(category:String) : List<Meal>{
        return api.getMeals(category).meals
    }

    suspend fun getCategories() : List<Category>{
        return api.getCategories().categories
    }

    suspend fun getMealById(mealId: String) : Meal?{
        return api.getMealById(mealId).meals.firstOrNull()
    }

    suspend fun saveFavorite(meal: Meal){
        val entity = MealEntity(
            mealId = meal.mealId,
            mealName = meal.mealName,
            mealImageUrl = meal.imageUrl
        )
        mealDao.insertFavorite(entity)
    }

    suspend fun removeFavorite(meal: Meal){
        val entity = MealEntity(
            mealId = meal.mealId,
            mealName = meal.mealName,
            mealImageUrl = meal.imageUrl
        )
        mealDao.deleteFavorite(entity)
    }

    fun isMealFavorite(mealId: String): Flow<Boolean>{
        return mealDao.isFavorite(mealId)
    }
}