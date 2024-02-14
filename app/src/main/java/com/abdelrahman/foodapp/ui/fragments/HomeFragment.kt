package com.abdelrahman.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelrahman.foodapp.Constants
import com.abdelrahman.foodapp.R
import com.abdelrahman.foodapp.ui.activities.MainActivity
import com.abdelrahman.foodapp.ui.activities.MealActivity
import com.abdelrahman.foodapp.adapters.CategoriesAdapter
import com.abdelrahman.foodapp.adapters.MostPopularAdapter
import com.abdelrahman.foodapp.ui.bottomsheet.MealBottomSheet
import com.abdelrahman.foodapp.databinding.FragmentHomeBinding
import com.abdelrahman.foodapp.data.pojo.Category
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.data.pojo.PopularMeal
import com.abdelrahman.foodapp.mvvm.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

  private lateinit var binding:FragmentHomeBinding
  private lateinit var viewModel:HomeViewModel
  private lateinit var randomMeal: Meal
  private var mostPopularAdapter = MostPopularAdapter()
    private var categoriesAdapter = CategoriesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel =(activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("fun","onCreateView")
        observeRandomMeal()
        onPopularMealClickListener()
        setupPopularMealsList()
        observePopularMealsList()
        onRandomMealCardClickListener()
        setupCategoriesList()
        observeCategories()
        onCategoriesClickListener()
        onLongPopularMealClickListener()
        onSearchBtnClickListener()
         return  binding.root
    }


    override fun onResume() {
        super.onResume()
        val navBar = requireActivity().findViewById<BottomNavigationView>(com.abdelrahman.foodapp.R.id.btn_nav)
        navBar.visibility = View.VISIBLE
    }
    private fun onSearchBtnClickListener(){
        binding.btnSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }
    private fun onPopularMealClickListener() {
        mostPopularAdapter.onCardClick={
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(Constants.KEY_MEAL_NAME,it.strMeal)
            intent.putExtra(Constants.KEY_MEAL_ID,it.idMeal)
            intent.putExtra(Constants.MEAL_PHOTO,it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun onLongPopularMealClickListener(){
        mostPopularAdapter.onCardLongClick = {
        val bottomSheetMealFragment = MealBottomSheet.newInstance(it.idMeal)
            bottomSheetMealFragment.show(childFragmentManager,"Meal Info")
        }
    }
    private fun onCategoriesClickListener(){
        categoriesAdapter.onCardClick={
            val bundle = Bundle()
            bundle.putString("categoryName",it.strCategory)
            findNavController().navigate(R.id.action_homeFragment_to_categoriesMealsFragment,bundle)

        }
    }

    private fun onRandomMealCardClickListener(){
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(Constants.KEY_MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(Constants.KEY_MEAL_ID,randomMeal.idMeal)
            intent.putExtra(Constants.MEAL_PHOTO,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private  fun observeRandomMeal(){
        Log.d("fun","observeRandomMeal")
        viewModel.observeRandomMeal().observe(viewLifecycleOwner
        ) {
            randomMeal = it
            Glide.with(this@HomeFragment).load(it.strMealThumb).into(binding.randomMealIv)
            GlobalScope.launch(Dispatchers.Main) {
                delay(3500)
                binding.randomMealShimmerIv.root.visibility=View.GONE
                binding.randomMealCard.visibility = View.VISIBLE
            }
        }
    }

    private fun setupPopularMealsList(){
        binding.populateMealsRv.apply {
            adapter = mostPopularAdapter
            layoutManager = LinearLayoutManager(requireContext(),GridLayoutManager.HORIZONTAL,false)
        }
    }
    private fun setupCategoriesList(){
        binding.categoriesRv.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
        }
    }

    private fun observePopularMealsList(){
        Log.d("fun","observePopularMealsList")
        viewModel.observePopularMeals().observe(viewLifecycleOwner){
            mostPopularAdapter.setMealsList(it as ArrayList<PopularMeal>)
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                binding.popularMealsShimmer.visibility = View.GONE
                binding.populateMealsRv.visibility = View.VISIBLE
            }

        }
    }
    private fun observeCategories(){
        Log.d("fun","observeCategories")
        viewModel.observeCategories().observe(viewLifecycleOwner){
            categoriesAdapter.setCategoriesList(it as ArrayList<Category>)
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                binding.categoriesShimmer.visibility = View.INVISIBLE
                binding.categoriesRv.visibility = View.VISIBLE
            }

        }
    }


}