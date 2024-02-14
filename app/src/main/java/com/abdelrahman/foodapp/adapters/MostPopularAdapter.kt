package com.abdelrahman.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.foodapp.databinding.PopularItemCardBinding
import com.abdelrahman.foodapp.data.pojo.PopularMeal
import com.abdelrahman.foodapp.data.pojo.PopularMeals
import com.bumptech.glide.Glide

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private var popularMealsList:ArrayList<PopularMeal> = ArrayList()
    lateinit var onCardClick:((PopularMeal)->Unit)
    lateinit var onCardLongClick:((PopularMeal)->Unit)
    fun setMealsList(meals: ArrayList<PopularMeal>){
        popularMealsList = meals
        notifyDataSetChanged()
    }
    class PopularMealViewHolder(val binding: PopularItemCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return popularMealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularMealsList[position].strMealThumb)
            .into(holder.binding.imgPopularItem)
      holder.itemView.setOnClickListener {
          onCardClick.invoke(popularMealsList[position])
      }
        holder.itemView.setOnLongClickListener {
            onCardLongClick.invoke(popularMealsList[position])
            true
        }
    }

}