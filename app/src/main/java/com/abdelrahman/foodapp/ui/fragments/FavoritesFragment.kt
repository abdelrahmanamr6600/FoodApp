package com.abdelrahman.foodapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.foodapp.ui.activities.MainActivity
import com.abdelrahman.foodapp.adapters.FavoritesMealsAdapter
import com.abdelrahman.foodapp.databinding.FragmentFavoritesBinding
import com.abdelrahman.foodapp.mvvm.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
        favoritesMealsAdapter = FavoritesMealsAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        observeFavoriteMeals()
        setupMealsRecyclerView()
        deleteMealFromDb()
        return binding.root
    }
    private fun setupMealsRecyclerView(){
        binding.favoriteMealsRv.apply {
            adapter = favoritesMealsAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }

    private fun observeFavoriteMeals(){
        viewModel.observeFavoriteMeals().observe(viewLifecycleOwner){
            if (it.isNotEmpty())
            {
                favoritesMealsAdapter.diff.submitList(it)
                binding.noMealsTv.visibility = View.INVISIBLE
                binding.favoriteMealsRv.visibility = View.VISIBLE
            }
        }
    }
    private fun deleteMealFromDb(){
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =  viewHolder.adapterPosition
                val deletedMeal = favoritesMealsAdapter.diff.currentList[position]
                viewModel.deleteMealDb(favoritesMealsAdapter.diff.currentList[position])
                Snackbar.make(requireView(),"Meal Was Deleted Successfully",Snackbar.LENGTH_SHORT).setAction("Undo") {
                    viewModel.insertMealDb(deletedMeal)
                }.show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoriteMealsRv)
    }


}