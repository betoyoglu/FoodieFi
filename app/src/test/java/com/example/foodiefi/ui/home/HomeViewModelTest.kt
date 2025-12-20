package com.example.foodiefi.ui.home

import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.data.repository.MealRepository
import com.example.foodiefi.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest (){
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    //mock repository
    private val repository: MealRepository = mockk(relaxed = true)

    //test edilecek viewmodel
    private lateinit var viewModel: HomeViewModel

    //her testten önce viewmodel yeniden oluşsun
    @Before
    fun setup(){
        io.mockk.mockkStatic(android.util.Log::class)

        io.mockk.every { android.util.Log.e(any(), any()) } returns 0
        io.mockk.every { android.util.Log.d(any(), any()) } returns 0

        io.mockk.every { android.util.Log.e(any(), any(), any()) } returns 0


        viewModel = HomeViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `arama metni degistiginde ve sure doldugunda searchResults guncellenmeli`() = runTest(testDispatcher) {
        val query = "chicken"
        val fakeMeals = listOf(
            Meal("1", "chicken soup", "a", "a", "a", "a", "a", "a", "a", "a", "a","a", "a", "a", "a", "a" ,"a")
        )

        //repoya ne yapması gerektiğini söylemek
        coEvery { repository.searchMeals(query) } returns fakeMeals

        //gereksiz bekleme olmasın diye
        advanceUntilIdle()

        //sonuç geldi mi
        val currentResults = viewModel.searchResults.value

        assertEquals(1, currentResults.size)
        assertEquals("chicken soup", currentResults.first().mealName)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `arama metni bos ise sonuclar temizlenmeli`() = runTest(testDispatcher) {
        viewModel.onQueryChange("")
        advanceUntilIdle()

        val currentResults = viewModel.searchResults.value
        assertEquals(0, currentResults.size)
    }

}