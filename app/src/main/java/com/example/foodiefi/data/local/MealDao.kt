package com.example.foodiefi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodiefi.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(meal: MealEntity)

    @Delete
    suspend fun deleteFavorite(meal: MealEntity)

    @Query("select * from favorites_table")
    fun getAllFavorites(): Flow<List<MealEntity>>

    @Query("select exists(select 1 from favorites_table where mealId = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>


}