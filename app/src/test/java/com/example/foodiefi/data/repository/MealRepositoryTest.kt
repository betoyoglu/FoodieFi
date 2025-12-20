package com.example.foodiefi.data.repository

import com.example.foodiefi.data.api.FoodApi
import com.example.foodiefi.data.local.MealDao
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.data.model.MealResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MealRepositoryTest {
    private val api : FoodApi = mockk()
    private val dao : MealDao = mockk(relaxed = true)

    private lateinit var repository : MealRepository

    @Before
    fun setup(){
        repository = MealRepository(api,dao)
    }

    @Test
    fun `searchMeals API basarili donerse dogru listeyi dondurmeli`() = runTest {
        val query = "chicken"
        val fakeMeal = Meal(
            "1", "chicken soup", "url", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo", "skdo"
        )

        val fakeResponse = MealResponse(meals = listOf(fakeMeal))

        coEvery { api.searchMeals(query) } returns fakeResponse

        val result = repository.searchMeals(query)

        assertEquals(1, result.size)
        assertEquals("chicken soup", result.first().mealName)
    }


}