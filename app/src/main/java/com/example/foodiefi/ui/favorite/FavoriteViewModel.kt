package com.example.foodiefi.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.data.repository.MealRepository
import com.example.foodiefi.data.repository.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface FavoritesUiState{
    object Loading : FavoritesUiState
    data class Success (val meals : List<Meal>): FavoritesUiState
    object Empty : FavoritesUiState
}

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: MealRepository) : ViewModel(){
    //veritabanını dinleyip entityi meal'e dönüştürme
    val uiState: StateFlow<FavoritesUiState> = repository.getAllFavorites()
        .map { entities ->
            if (entities.isEmpty()){
                FavoritesUiState.Empty
            }else{
                FavoritesUiState.Success(entities.map { it.toDomainModel() })
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )
}