package com.example.foodiefi.data.model

import com.google.gson.annotations.SerializedName

//jsondan geleni karşılasın diye

//tek bir yemek
data class Meal(
    @SerializedName("idMeal")
    val mealId: String,
    @SerializedName("strMeal")
    val mealName: String,
    @SerializedName("strMealThumb")
    val imageUrl : String,
    @SerializedName("strCategory")
    val mealCategory: String,
    @SerializedName("strArea")
    val mealArea: String,
    @SerializedName("strInstructions")
    val mealInstructions: String,
    @SerializedName("strYoutube")
    val mealYoutubeLink: String,

    //measure ve ingredients liste halinde verilmemiş bu yüzden:
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,

    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?
){
    //ui için liste:
    fun getIngredientsWithMeasures() : List<Pair<String, String>>{
        val list = mutableListOf<Pair<String, String>>()
        fun addIfValid(ingridient: String?, measure: String?){
            if(!ingridient.isNullOrBlank() && !measure.isNullOrBlank()){
                list.add(ingridient to measure) // to burada pair anlamına geliyor. uida alırken first ve second olarak alıcaz
            }
        }
        addIfValid(strIngredient1, strMeasure1)
        addIfValid(strIngredient2, strMeasure2)
        addIfValid(strIngredient3, strMeasure3)
        addIfValid(strIngredient4, strMeasure4)
        addIfValid(strIngredient5, strMeasure5)

        return list
    }
}

//apiden gelen
data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>
)
