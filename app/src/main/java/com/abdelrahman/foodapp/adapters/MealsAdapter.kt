package com.abdelrahman.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abdelrahman.foodapp.databinding.MealItemBinding
import com.abdelrahman.foodapp.data.pojo.Meal
import com.abdelrahman.foodapp.data.pojo.PopularMeal
import com.bumptech.glide.Glide

class MealsAdapter:RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {
    private var mealsList = ArrayList<Meal>()
    lateinit var onCardClick:((Meal)->Unit)
    fun setMealsList(mealsList:ArrayList<Meal>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
     class MealsViewHolder(val binding:MealItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return(MealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }

    override fun getItemCount(): Int {
         return mealsList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.mealIv)
        holder.binding.mealName.text = mealsList[position].strMeal
        holder.binding.root.setOnClickListener {
            onCardClick.invoke(mealsList[position])
        }

    }
}