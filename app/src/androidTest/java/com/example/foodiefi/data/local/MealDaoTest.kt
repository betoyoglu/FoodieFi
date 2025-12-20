package com.example.foodiefi.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.foodiefi.data.local.entity.MealEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MealDaoTest {
    private lateinit var database: FoodDatabase
    private lateinit var dao: MealDao

    @Before
    fun setup(){
        //ram üzerinde çalışan geçici bir veritabanı
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.mealDao
    }

    @After
    fun teardown(){
        database.close() //test bitince kapansın
    }

    @Test
    fun insertMeal_and_getFavorites() = runTest {
        val meal = MealEntity("1", "chicken soup", "url", "skdo")

        dao.insertFavorite(meal)

        val favorites = dao.getAllFavorites().first()

        assertEquals(1, favorites.size)
        assertEquals("chicken soup", favorites[0].mealName)
    }
}