package com.abdelrahman.foodapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdelrahman.foodapp.data.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)
    @Delete()
    suspend fun delete(meal: Meal)
    @Query("select*from MealsInformation")
    fun getMeals():LiveData<List<Meal>>
}