package com.abdelrahman.foodapp.data.retrofit

import com.abdelrahman.foodapp.data.pojo.Categories
import com.abdelrahman.foodapp.data.pojo.MealsCategories
import com.abdelrahman.foodapp.data.pojo.MealsList
import com.abdelrahman.foodapp.data.pojo.PopularMeals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    fun getRandomMeals():Call<MealsList>
    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String):Call<MealsList>
    @GET("filter.php")
    fun getPopularMeals(@Query("c") category:String):Call<PopularMeals>
    @GET("categories.php")
    fun getCategories():Call<Categories>
    @GET("Filter.php")
    fun getMealsByCategories(@Query("c") categoryName:String):Call<MealsCategories>
    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery:String):Call<MealsList>
}