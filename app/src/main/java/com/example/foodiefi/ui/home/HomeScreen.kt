package com.example.foodiefi.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodiefi.ui.components.CategoryFilterChip
import com.example.foodiefi.ui.components.SearchBarM3

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()){
    //viewmodeldeki stateleri dinle (collect)
    val categoryList by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val queryText by viewModel.queryText.collectAsStateWithLifecycle()
    val isActive by viewModel.isActive.collectAsStateWithLifecycle()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ){
        SearchBarM3(
            query = queryText,
            onQueryChange = {queryText ->
                viewModel.onQueryChange(queryText)
            },
            onSearch = {query ->
                viewModel.onSearch(query)
            },
            active = isActive,
            onActiveChange = {newActive ->
                viewModel.onActiveChange(newActive)
            }
        )

        //category chips
        LazyRow (
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ){
            items(categoryList) {category ->
                CategoryFilterChip(
                    isSelected = (category.categoryName == selectedCategory),
                    text = category.categoryName,
                    onClick = {
                        viewModel.onCategorySelected(category.categoryName)
                    },
                    chipImage = category.categoryImageUrl
                )
            }
        }
    }

}