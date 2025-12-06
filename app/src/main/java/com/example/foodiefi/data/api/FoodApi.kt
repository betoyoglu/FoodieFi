package com.example.foodiefi.data.api

import com.example.foodiefi.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    //temel url appmodulde burda sadece endpointleri yazıcaz

    @GET("filter.php")
    suspend fun getMeals(
        @Query("c") category: String = "Seafood" //varsayılan
    ) : MealResponse
}