package com.abdelrahman.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abdelrahman.foodapp.databinding.MealItemBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.bumptech.glide.Glide

class FavoritesMealsAdapter:RecyclerView.Adapter<FavoritesMealsAdapter.MealsViewHolder>() {
    private val diffUtil = object: DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return (oldItem.idMeal==newItem.idMeal)
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this,diffUtil)
    inner class MealsViewHolder(val binding: MealItemBinding):ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return diff.currentList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(diff.currentList[position].strMealThumb)
            .into(holder.binding.mealIv)
        holder.binding.mealName.text = diff.currentList[position].strMeal
    }
}