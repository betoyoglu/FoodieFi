package com.example.foodiefi.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefi.data.model.Meal
import com.example.foodiefi.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailUiState{
    object Loading: DetailUiState
    data class Success(
        val meal: Meal,
        val time: String,
        val difficulty: String,
        val rating: String,
        val isFavorite: Boolean = false
    ) : DetailUiState
    object Error: DetailUiState
}

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MealRepository, savedStateHandle: SavedStateHandle): ViewModel()
{ //savedstatehandle navigasyonla gönderilen foodId parametresini otomatik olarak almayı sağlıyor

    //başlangıçta loading
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    // Geçici değişkenler (State güncellerken kaybolmasın diye)
    private var currentMealId: String = ""
    private var currentTime = ""
    private var currentDiff = ""
    private var currentRate = ""

    private var isFavoriteChecked = false

    init {
        val mealId = savedStateHandle.get<String>("mealId") ?: ""

        currentTime = savedStateHandle.get<String>("time") ?: ""
        currentDiff = savedStateHandle.get<String>("difficulty") ?: ""
        currentRate = savedStateHandle.get<String>("rating") ?: ""

        currentMealId = mealId

        if (mealId != "") {
            observeFavoriteStatus(mealId)
            getMealDetails(mealId)
        } else {
            _uiState.value = DetailUiState.Error
        }
    }

    private fun getMealDetails(id: String){
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val meal = repository.getMealById(id)
                if (meal != null) {
                    _uiState.value = DetailUiState.Success(
                        meal = meal,
                        time = currentTime,
                        difficulty = currentDiff,
                        rating = currentRate,
                        isFavorite = isFavoriteChecked
                    )
                }else{
                    _uiState.value = DetailUiState.Error
                }
            }catch (e: Exception){
                _uiState.value = DetailUiState.Error
            }
        }
    }

    private fun observeFavoriteStatus(mealId: String){
        viewModelScope.launch {
            repository.isMealFavorite(mealId).collect { isFav->
                isFavoriteChecked = isFav

                _uiState.update { currentState ->
                    if (currentState is DetailUiState.Success){
                        currentState.copy(isFavorite =  isFav)
                    }else{
                        currentState
                    }
                }
            }
        }
    }

    fun onFavoriteClick() {
        val currentState = _uiState.value
        if (currentState is DetailUiState.Success) {
            viewModelScope.launch {
                if (currentState.isFavorite) {
                    // Zaten favoriyse -> SİL
                    repository.removeFavorite(currentState.meal)
                } else {
                    // Favori değilse -> EKLE
                    repository.saveFavorite(currentState.meal)
                }
            }
        }
    }
}