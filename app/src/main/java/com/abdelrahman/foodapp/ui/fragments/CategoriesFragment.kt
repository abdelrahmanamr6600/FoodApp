package com.abdelrahman.foodapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.abdelrahman.foodapp.R
import com.abdelrahman.foodapp.ui.activities.MainActivity
import com.abdelrahman.foodapp.adapters.CategoriesAdapter
import com.abdelrahman.foodapp.databinding.FragmentCategoriesBinding
import com.abdelrahman.foodapp.data.pojo.Category
import com.abdelrahman.foodapp.mvvm.HomeViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
   private lateinit var categoriesAdapter: CategoriesAdapter
   private lateinit var homeViewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        observeCategories()
        setupCategoriesRv()
        onCategoryCardClickListener()
        return binding.root
    }
    private fun setupCategoriesRv()
    {

        binding.categoriesRv.apply {
            categoriesAdapter = CategoriesAdapter()
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    private fun observeCategories(){
        homeViewModel.observeCategories().observe(viewLifecycleOwner){
            responseCase()
            categoriesAdapter.setCategoriesList(it as ArrayList<Category>)

        }
    }
    private fun onCategoryCardClickListener(){
        categoriesAdapter.onCardClick = {
            val bundle = Bundle()
            bundle.putString("categoryName",it.strCategory)
            findNavController().navigate(R.id.action_categoriesFragment_to_categoriesMealsFragment,bundle)
        }
    }
private fun responseCase(){
    binding.shimmerOne.visibility=View.INVISIBLE
    binding.shimmerTwo.visibility=View.INVISIBLE
    binding.categoriesRv.visibility=View.VISIBLE
}

}