package com.example.foodiefi.data.api

import com.example.foodiefi.data.model.CategoryResponse
import com.example.foodiefi.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    //temel url appmodulde burda sadece endpointleri yazıcaz

    @GET("filter.php")
    suspend fun getMeals(@Query("c") category: String) : MealResponse

    @GET("categories.php")
    suspend fun getCategories() : CategoryResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse
}