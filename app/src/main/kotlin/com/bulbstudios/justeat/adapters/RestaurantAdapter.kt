package com.bulbstudios.justeat.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bulbstudios.justeat.R
import com.bulbstudios.justeat.databinding.CellResturantBinding
import com.bulbstudios.justeat.dataclasses.Restaurant

/**
 * Created by Terence Baker on 12/05/2016.
 */
class RestaurantAdapter(var restaurantList: List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<CellResturantBinding>(LayoutInflater.from(parent.context), R.layout.cell_resturant, parent, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.restaurant = restaurantList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = restaurantList.size

    class ViewHolder(val binding: CellResturantBinding) : RecyclerView.ViewHolder(binding.root) {}
}