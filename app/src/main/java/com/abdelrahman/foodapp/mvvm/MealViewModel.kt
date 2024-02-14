package com.abdelrahman.foodapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.foodapp.data.db.MealDataBase
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.data.pojo.MealsList
import com.abdelrahman.foodapp.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {
    private var randomMeal:MutableLiveData<Meal> = MutableLiveData()
    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object:Callback<MealsList>{
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {
                randomMeal.postValue(response.body()!!.meals[0])

            }

            override fun onFailure(call: Call<MealsList>, t: Throwable) {
                Log.d("MealViewModel",t.message!!)
            }

        })
    }
    fun observeMealLiveDate():LiveData<Meal>{
        return randomMeal
    }
    fun insertMealDb(meal: Meal){
      viewModelScope.launch {
          mealDataBase.mealDao().upsert(meal)
      }
    }


}