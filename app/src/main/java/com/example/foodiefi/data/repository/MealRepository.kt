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
            mealImageUrl = meal.imageUrl,
            mealCategory = meal.mealCategory
        )
        mealDao.insertFavorite(entity)
    }

    suspend fun removeFavorite(meal: Meal){
        val entity = MealEntity(
            mealId = meal.mealId,
            mealName = meal.mealName,
            mealImageUrl = meal.imageUrl,
            mealCategory = meal.mealCategory
        )
        mealDao.deleteFavorite(entity)
    }

    fun isMealFavorite(mealId: String): Flow<Boolean>{
        return mealDao.isFavorite(mealId)
    }

    fun getAllFavorites() : Flow<List<MealEntity>>{
        return mealDao.getAllFavorites()
    }

    suspend fun searchMeals(query: String): List<Meal>{
        return try {
            val response = api.searchMeals(query)
            response.meals ?: emptyList()
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}

// MealEntity'yi Meal sınıfına çeviren yardımcı fonksiyon
fun MealEntity.toDomainModel(): Meal {
    return Meal(
        mealId = this.mealId,
        mealName = this.mealName,
        imageUrl = this.mealImageUrl,
        mealCategory = this.mealCategory,
        mealArea = "",
        mealInstructions = "",
        mealYoutubeLink = "",
        strIngredient1 = null, strIngredient2 = null, strIngredient3 = null, strIngredient4 = null, strIngredient5 = null,
        strMeasure1 = null, strMeasure2 = null, strMeasure3 = null, strMeasure4 = null, strMeasure5 = null
    )
}