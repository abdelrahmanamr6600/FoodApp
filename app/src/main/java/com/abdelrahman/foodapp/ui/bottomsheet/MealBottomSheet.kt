package com.abdelrahman.foodapp.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abdelrahman.foodapp.Constants
import com.abdelrahman.foodapp.R
import com.abdelrahman.foodapp.ui.activities.MainActivity
import com.abdelrahman.foodapp.ui.activities.MealActivity
import com.abdelrahman.foodapp.databinding.FragmentMealBottomSheetBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.mvvm.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"



class MealBottomSheet : BottomSheetDialogFragment() {
    private lateinit var  binding:FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var meal: Meal

    private var mealId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentMealBottomSheetBinding.inflate(layoutInflater)
        viewModel =(activity as MainActivity).viewModel
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel.getMealById(mealId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeMeal()
        onFragmentClickListener()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
    private fun onFragmentClickListener(){
        binding.bottomSheet.setOnClickListener {
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(Constants.KEY_MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.KEY_MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_PHOTO,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeMeal(){
        viewModel.observeMealDetails().observe(viewLifecycleOwner){
            meal = it
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.mealIv)
            binding.tvMealCategory.text = it.strCategory
            binding.tvMealCountry.text = it.strArea
            binding.tvMealNameInBtmsheet.text = it.strMeal

        }

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


}