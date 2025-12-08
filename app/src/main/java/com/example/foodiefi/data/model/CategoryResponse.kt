package com.example.foodiefi.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    val categoryId: String,
    @SerializedName("strCategory")
    val categoryName: String,
    @SerializedName("strCategoryThumb")
    val categoryImageUrl: String,
)

data class CategoryResponse(
    @SerializedName("categories")
    val categories : List<Category>
)