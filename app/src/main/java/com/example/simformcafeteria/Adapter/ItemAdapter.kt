package com.example.simformcafeteria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simformcafeteria.R

class ItemAdapter(var itemList: ArrayList<ItemDataClass>, val context: Context): RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return MyViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {
        holder.apply {
           with(itemList[position]){
               imgLike.setImageResource(like)
               imgDesLike.setImageResource(this.desLike)
               menuTime.text = this.timeOfText
               textMenu.text = this.timeText
           }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var menuTime: TextView = itemView.findViewById(R.id.morningTextTea)
        var textMenu: TextView = itemView.findViewById(R.id.dailyDate)
        var btnSelectionTea: Button = itemView.findViewById(R.id.btnTea)
        var btnSelectionCoffee: Button = itemView.findViewById(R.id.btnCoffee)
        var imgLike: ImageButton = itemView.findViewById(R.id.btnLike)
        var imgDesLike: ImageButton = itemView.findViewById(R.id.btnDesLike)
    }
}