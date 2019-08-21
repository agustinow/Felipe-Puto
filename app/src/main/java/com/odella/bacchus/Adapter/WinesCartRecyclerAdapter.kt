package com.odella.bacchus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odella.bacchus.Model.WineCartFeed
import com.odella.bacchus.R

class WinesCartRecyclerAdapter(val context: Context, val onDeleteClick: (WineCartFeed) -> (Unit), val onNumChange: (WineCartFeed, Int) -> (Unit)) : RecyclerView.Adapter<WinesCartRecyclerAdapter.ViewHolder>() {
    var list: MutableList<WineCartFeed> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinesCartRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.element_wine_cart, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WinesCartRecyclerAdapter.ViewHolder, position: Int) {
        val winecart = list[holder.adapterPosition]

        holder.txtWineName.text = winecart.wine.name
        holder.txtWinePrice.text = "$${winecart.wine.price}"
        holder.numAmount.value = winecart.amount
        holder.numAmount.maxValue = 10
        holder.numAmount.minValue = 1
        holder.imgWineColor.background = when(winecart.wine.color){
            "red" -> context.resources.getDrawable(context.resources.getIdentifier("grape_normal", "drawable", context.packageName), context.resources.newTheme())
            "rose" -> context.resources.getDrawable(context.resources.getIdentifier("grape_rose", "drawable", context.packageName), context.resources.newTheme())
            "white" -> context.resources.getDrawable(context.resources.getIdentifier("grape_white", "drawable", context.packageName), context.resources.newTheme())
            else -> context.resources.getDrawable(context.resources.getIdentifier("grape_icon", "drawable", context.packageName), context.resources.newTheme())
        }
        Glide.with(holder.itemView.context).load(winecart.wine.image).into(holder.imgWine)
        holder.btnDelete.setOnClickListener {
            onDeleteClick(winecart)
        }
        holder.numAmount.setOnValueChangedListener { numberPicker, i, j ->
            onNumChange(winecart, j)
        }
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val momlayout: ConstraintLayout = itemView!!.findViewById(R.id.element_wine_cart_layout)
        val btnDelete: ImageButton = itemView!!.findViewById(R.id.btnWineCartDelete)
        val imgWine: ImageView = itemView!!.findViewById(R.id.imgElementWineCart)
        val imgWineColor: ImageView = itemView!!.findViewById(R.id.imgWineCartColor)
        val txtWineName: TextView = itemView!!.findViewById(R.id.txtWineCartName)
        val txtWinePrice: TextView = itemView!!.findViewById(R.id.txtWineCartPrice)
        val numAmount: NumberPicker = itemView!!.findViewById(R.id.numWineCartAmount)
    }

}