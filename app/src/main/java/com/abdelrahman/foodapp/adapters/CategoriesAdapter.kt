package com.abdelrahman.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abdelrahman.foodapp.databinding.CategoryItemBinding
import com.abdelrahman.foodapp.data.pojo.Category
import com.abdelrahman.foodapp.data.pojo.Meal
import com.bumptech.glide.Glide

class CategoriesAdapter:RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var categoriesList:ArrayList<Category> = ArrayList()
     lateinit var onCardClick:((Category)->Unit)
    fun setCategoriesList(categoriesList:ArrayList<Category>){
        this.categoriesList = categoriesList
        notifyDataSetChanged()

    }
   inner class CategoriesViewHolder( val binding: CategoryItemBinding):ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.categoryIv)
        holder.binding.categoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onCardClick.invoke(categoriesList[position])
        }
    }
}