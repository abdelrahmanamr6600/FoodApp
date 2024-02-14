package com.abdelrahman.foodapp.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.abdelrahman.foodapp.ui.activities.MainActivity
import com.abdelrahman.foodapp.adapters.SearchedMealsAdapter
import com.abdelrahman.foodapp.databinding.FragmentSearchBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.mvvm.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchFragment : Fragment() {
    private  lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchedMealsAdapter: SearchedMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
        val navBar = requireActivity().findViewById<BottomNavigationView>(com.abdelrahman.foodapp.R.id.btn_nav)
        navBar.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
       onSearchBtnClickListener()
        observeMealsList()
        setupMealsRecyclerView()

        return binding.root
    }
    private fun setupMealsRecyclerView(){
        searchedMealsAdapter = SearchedMealsAdapter()
        binding.mealsRv.apply {
            adapter = searchedMealsAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }
    private fun onSearchBtnClickListener(){
      binding.icSearch.setOnClickListener {
          searchMeals()
          hideKeyboard(requireActivity())
      }
    }
    private fun searchMeals() {
        val query = binding.edSearch.text.toString()
        if (query!=null)
           viewModel.searchMeals(query)
    }
    private fun observeMealsList(){
        viewModel.observeSearchedMealsList().observe(viewLifecycleOwner){
            Log.d("size",it.size.toString())
            searchedMealsAdapter.setMealsList(it as ArrayList<Meal>)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}