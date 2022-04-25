package com.example.simformcafeteria.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simformcafeteria.model.ItemDataClass
import com.example.simformcafeteria.databinding.ItemRecyclerBinding

class ItemAdapter(var itemList: ArrayList<ItemDataClass>, val context: Context): RecyclerView.Adapter<ItemAdapter. MenuItemViewHolder>() {

    lateinit var binding : ItemRecyclerBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val viewHolder = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  MenuItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder:  MenuItemViewHolder, position: Int) {
        binding.itemViewModel = itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MenuItemViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        //Will be Implemented Later
    }
}