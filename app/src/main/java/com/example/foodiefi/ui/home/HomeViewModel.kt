package com.example.foodiefi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefi.data.model.Category
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.data.repository.MealRepository
import com.example.foodiefi.ui.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MealRepository) : ViewModel()
{
    //kategorileri tutan state
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    //seçilen kategori statei
    private val _selectedCategory = MutableStateFlow("Beef")
    val selectedCategory = _selectedCategory.asStateFlow()

    //search stateleri
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive = _isActive.asStateFlow()

    private val _recommendations = MutableStateFlow<List<MealUiState>>(emptyList())
    val recommendations = _recommendations.asStateFlow()

    private val _recipesOfTheWeek = MutableStateFlow<List<MealUiState>>(emptyList())
    val recipesOfTheWeek = _recipesOfTheWeek.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MealUiState>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private var searchJob : Job? = null

    init {
        fetchCategories()
        fetchRecommendations(_selectedCategory.value)
    }

    private fun mapToUiState(apiMeals: List<Meal>): List<MealUiState> {
        return apiMeals.map { meal ->
            MealUiState(
                mealId = meal.mealId,
                mealName = meal.mealName,
                mealImageUrl = meal.imageUrl,
                mealDescription = meal.mealInstructions ?: "Delicious recipe...",
                time = "${(15..60).random()} mins",
                difficulty = listOf("Easy", "Medium", "Hard").random(),
                rating = String.format("%.1f", Random.nextDouble(2.5, 5.0))
            )
        }
    }

    private fun fetchRecommendations(category: String) {
        viewModelScope.launch {
            try {
                val apiMeals = repository.getMeals(category = category)
                android.util.Log.d("FoodieFi", "Kategori: $category, Gelen Yemek Sayısı: ${apiMeals.size}")
                val uiMeals = apiMeals.map { meal ->
                    MealUiState(
                        mealId = meal.mealId,
                        mealName = meal.mealName,
                        mealImageUrl = meal.imageUrl,
                        mealDescription = meal.mealInstructions ?: "Delicious recipe for ${meal.mealName}...",
                        time = "${(15..60).random()} mins",
                        difficulty = listOf("Easy", "Medium", "Hard").random(),
                        rating = String.format("%.1f", Random.nextDouble(2.5, 5.0))
                        //rating = Random.nextInt(")
                    )
                }
                _recommendations.value = uiMeals

                _recipesOfTheWeek.value = uiMeals.shuffled()

                if (_queryText.value.isNotEmpty()){
                    filterMeals(_queryText.value)
                }
            }catch (e: Exception){
                android.util.Log.e("FoodieFi", "Hata Oluştu: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                _categories.value = repository.getCategories()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun onCategorySelected(categoryName: String){
        _selectedCategory.value = categoryName
        fetchRecommendations(categoryName)
    }

    fun onQueryChange(newQuery : String){
        _queryText.value = newQuery
        searchJob?.cancel()

        if(newQuery.isEmpty()){
            _searchResults.value = emptyList()
        }else{
            searchJob = viewModelScope.launch {
                delay(500)
                try {
                    val results = repository.searchMeals(newQuery)
                    _searchResults.value = mapToUiState(results)
                }catch (e: Exception){
                    e.printStackTrace()
                    _searchResults.value = emptyList()
                }
            }
        }
    }

    private fun filterMeals(query : String){
        if(query.isNotEmpty()){
            _searchResults.value = emptyList()
        }else{
            val currentList = _recommendations.value
            _searchResults.value = currentList.filter { meal ->
                meal.mealName.contains(query, ignoreCase = true)
            }
        }
    }

    fun onActiveChange(newActive: Boolean){
        _isActive.value = newActive
    }

    fun onSearch(query: String){ //search butonu için
        _isActive.value = false
    }
}