package com.example.foodiefi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefi.data.model.Category
import com.example.foodiefi.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MealRepository) : ViewModel()
{
    //kategorileri tutan state
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    //seçilen kategori statei
    private val _selectedCategory = MutableStateFlow("Soup")
    val selectedCategory = _selectedCategory.asStateFlow()

    //search stateleri
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _isActive = MutableStateFlow(false)
    val isActive = _isActive.asStateFlow()

    init {
        fetchCategories()
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
    }

    fun onQueryChange(newQuery : String){
        _queryText.value = newQuery
    }

    fun onActiveChange(newActive: Boolean){
        _isActive.value = newActive
    }

    fun onSearch(query: String){ //search butonu için
        _isActive.value = false
    }
}