package com.abdelrahman.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.abdelrahman.foodapp.Constants
import com.abdelrahman.foodapp.adapters.MealsAdapter
import com.abdelrahman.foodapp.databinding.FragmentCategoriesMealsBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.mvvm.CategoriesMealsViewModel
import com.abdelrahman.foodapp.ui.activities.MealActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class CategoriesMealsFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesMealsBinding
    private lateinit var categoryViewModel: CategoriesMealsViewModel
    private lateinit var categoryName:String
    private lateinit var mealsAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = requireArguments().getString("categoryName")!!

        val navBar = requireActivity().findViewById<BottomNavigationView>(com.abdelrahman.foodapp.R.id.btn_nav)
        navBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriesMealsBinding.inflate(layoutInflater)
        categoryViewModel = ViewModelProvider(this)[CategoriesMealsViewModel::class.java]
        binding.categoryName.text = categoryName
        categoryViewModel.getMeals(categoryName)
        observeMealsList()
        onMealCardClickListener()
        return binding.root
    }

    private fun observeMealsList(){
        mealsAdapter = MealsAdapter()
        categoryViewModel.observeMealsList().observe(viewLifecycleOwner){
            if (it.size>1){
                onResponseCase()


                binding.mealsRv.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
                binding.mealsRv.adapter = mealsAdapter
                mealsAdapter.setMealsList(it as ArrayList<Meal>)
            }
        }
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.mealsRv.visibility = View.VISIBLE

    }
    private fun onMealCardClickListener(){
        mealsAdapter.onCardClick ={
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(Constants.KEY_MEAL_NAME,it.strMeal)
            intent.putExtra(Constants.KEY_MEAL_ID,it.idMeal)
            intent.putExtra(Constants.MEAL_PHOTO,it.strMealThumb)
            startActivity(intent)
        }
    }

}