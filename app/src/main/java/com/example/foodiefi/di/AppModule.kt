package com.example.foodiefi.di

import android.app.Application
import androidx.room.Room
import com.example.foodiefi.data.api.FoodApi
import com.example.foodiefi.data.local.FoodDatabase
import com.example.foodiefi.data.local.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //tüm uygulama boyunca yaşasın diye
object AppModule {
    @Provides
    @Singleton
    fun provideFoodApi() : FoodApi {
        return Retrofit.Builder()
            .baseUrl("www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : FoodDatabase{
        return Room.databaseBuilder(
            app,
            FoodDatabase::class.java,
            "foodiefi_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMealDao(db: FoodDatabase) : MealDao{
        return db.mealDao
    }
}