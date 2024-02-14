package com.abdelrahman.foodapp.ui.activities
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.foodapp.Constants
import com.abdelrahman.foodapp.R
import com.abdelrahman.foodapp.data.db.MealDataBase
import com.abdelrahman.foodapp.databinding.ActivityMealBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.mvvm.MealViewModel
import com.abdelrahman.foodapp.mvvm.MealViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private  var mealVideo:String? =null
    private lateinit var mealDb: Meal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        val mealDataBase = MealDataBase.getInstance(this)
        val mealViewModelFactory = MealViewModelFactory(mealDataBase)
        mealViewModel = ViewModelProvider(this,mealViewModelFactory)[MealViewModel::class.java]
        loadingCase()
        getRandomMealFromIntent()
        observeMealLiveData()
        onYoutubeIconClick()
        onSaveBtnClickListener()
        setContentView(binding.root)
    }

    private fun onSaveBtnClickListener() {
        binding.favoriteBtn.setOnClickListener{
            mealViewModel.insertMealDb(mealDb)
            Toast.makeText(this,"Meal Was Saved Successfully",Toast.LENGTH_LONG).show()
        }
    }

    private fun onYoutubeIconClick() {
        binding.youtubeTv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealVideo))
            startActivity(intent)
        }

    }

    private fun getRandomMealFromIntent(){
        val intent = intent
        binding.collapsingToolBar.title = intent.getStringExtra(Constants.KEY_MEAL_NAME)
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
        Glide.with(this).load(intent.getStringExtra(Constants.MEAL_PHOTO)).into(binding.mealIv)
        mealViewModel.getMealDetails(intent.getStringExtra(Constants.KEY_MEAL_ID)!!)
    }

    private fun loadingCase(){
        binding.favoriteBtn.visibility = View.INVISIBLE
        binding.categoryTv.visibility = View.INVISIBLE
        binding.locationTv.visibility = View.INVISIBLE
        binding.contentTv.visibility = View.INVISIBLE
        binding.youtubeTv.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun responseCase(){
        binding.favoriteBtn.visibility = View.VISIBLE
        binding.categoryTv.visibility = View.VISIBLE
        binding.locationTv.visibility = View.VISIBLE
        binding.contentTv.visibility = View.VISIBLE
        binding.youtubeTv.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun observeMealLiveData(){
        mealViewModel.observeMealLiveDate().observe(this) {
            responseCase()
            mealDb = it
            binding.contentTv.text=it.strInstructions
            binding.categoryTv.text ="Category : ${it.strCategory}"
            binding.locationTv.text="Location : ${it.strArea}"
            mealVideo = it.strYoutube

        }
    }

}