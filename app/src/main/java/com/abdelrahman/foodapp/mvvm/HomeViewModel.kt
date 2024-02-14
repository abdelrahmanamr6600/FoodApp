package com.abdelrahman.foodapp.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.foodapp.data.db.MealDataBase
import com.abdelrahman.foodapp.data.pojo.Category
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.data.pojo.Categories
import com.abdelrahman.foodapp.data.pojo.MealsList
import com.abdelrahman.foodapp.data.pojo.PopularMeal
import com.abdelrahman.foodapp.data.pojo.PopularMeals
import com.abdelrahman.foodapp.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDataBase: MealDataBase):ViewModel() {
    private var randomMealList:MutableLiveData<Meal> = MutableLiveData()
    private var popularMeals = MutableLiveData<List<PopularMeal>>()
    private var categoryMeals = MutableLiveData<List<Category>>()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var searchResultLiveData = MutableLiveData<List<Meal>>()
    init {
        getRandomMeal()
        getCategories()
        getPopularMeals()
    }

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeals().enqueue(object :retrofit2.Callback<MealsList>{
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {
                if (response.body()!=null){
                    val meal = response.body()!!.meals[0]
                    randomMealList.postValue(meal)
                }
            }
            override fun onFailure(call: Call<MealsList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    fun getPopularMeals(){
        RetrofitInstance.api.getPopularMeals("Seafood").enqueue(object:Callback<PopularMeals>{
            override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
                if (response.body()!=null){
                    popularMeals.value = response.body()!!.meals
                }
            }
            override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
                Log.d("popularMeals",t.message.toString())
            }
        })
    }
    fun getMealById(mealId:String){
        RetrofitInstance.api.getMealDetails(mealId).enqueue(object:Callback<MealsList>{
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {
                if (response.body()!=null)
                    bottomSheetMealLiveData.postValue(response.body()!!.meals.first())
            }

            override fun onFailure(call: Call<MealsList>, t: Throwable) {
                Log.d("bottomSheetMeal",t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :retrofit2.Callback<Categories>{
            override fun onResponse(
                call: Call<Categories>,
                response: Response<Categories>
            ) {
                if (response.body()!=null){
                    categoryMeals.postValue(response.body()!!.categories)
                }
            }
            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.d("Categories",t.message.toString())
            }

        })
    }

    fun searchMeals(query:String){
        RetrofitInstance.api.searchMeals(query).enqueue(object :Callback<MealsList>{
            override fun onResponse(call: Call<MealsList>, response: Response<MealsList>) {
                if (response.body()!=null){
                    searchResultLiveData.postValue(response.body()!!.meals)
                    Log.d("searchSize",response.body()!!.meals.size.toString())
                }
            }

            override fun onFailure(call: Call<MealsList>, t: Throwable) {
                Log.d("SearchMeals",t.message.toString())
            }

        })
    }
    fun deleteMealDb(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun insertMealDb(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

     fun  observeRandomMeal():LiveData<Meal>{
        return randomMealList
    }
    fun observePopularMeals():LiveData<List<PopularMeal>> = popularMeals
    fun observeCategories():LiveData<List<Category>> =  categoryMeals
    fun observeFavoriteMeals():LiveData<List<Meal>> = mealDataBase.mealDao().getMeals()
    fun observeMealDetails():LiveData<Meal> = bottomSheetMealLiveData
    fun observeSearchedMealsList():LiveData<List<Meal>> = searchResultLiveData


}