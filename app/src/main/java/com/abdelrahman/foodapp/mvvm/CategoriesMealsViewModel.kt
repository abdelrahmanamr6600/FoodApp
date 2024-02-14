package com.abdelrahman.foodapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.data.pojo.MealsCategories
import com.abdelrahman.foodapp.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesMealsViewModel:ViewModel() {
    private var mealsList = MutableLiveData<List<Meal>>()

    fun getMeals(categoryName:String){
        RetrofitInstance.api.getMealsByCategories(categoryName).enqueue(object:
            Callback<MealsCategories> {
            override fun onResponse(
                call: Call<MealsCategories>,
                response: Response<MealsCategories>
            ) {
                mealsList.postValue(response.body()!!.meals)
            }

            override fun onFailure(call: Call<MealsCategories>, t: Throwable) {
            }
        })
    }
    fun observeMealsList():LiveData<List<Meal>>{
        return mealsList
    }
}