package com.odella.bacchus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odella.bacchus.Model.WineFeed
import com.odella.bacchus.R

class WinesRecyclerAdapter(val context: Context, val onClick: (WineFeed) -> (Unit), val onAddCart: (WineFeed) -> (Boolean) ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var isLinear = true
    var cartList : MutableSet<Int> = mutableSetOf()
    var winesList : List<WineFeed> = listOf()
    val differ = AsyncListDiffer<WineFeed>(this, object: DiffUtil.ItemCallback<WineFeed>(){
        override fun areItemsTheSame(oldItem: WineFeed, newItem: WineFeed): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: WineFeed, newItem: WineFeed): Boolean {
            return (oldItem.name == newItem.name && oldItem.color == newItem.color && oldItem.age == newItem.age)
        }
    })



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return if(isLinear) LinearViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.element_wine, parent, false))
        else GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.element_wine_image, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val wine = differ.currentList[holder.adapterPosition]

        if(holder is LinearViewHolder){
            Glide.with(holder.itemView.context).load(wine.image).into(holder.imgWine)
            holder.txtWine.text = wine.name!!.capitalize()
            holder.txtPrice.text = "$${wine.price}"
            holder.imgWineColor.background = when(wine.color){
                "red" -> context.resources.getDrawable(context.resources.getIdentifier("grape_normal", "drawable", context.packageName), context.resources.newTheme())
                "rose" -> context.resources.getDrawable(context.resources.getIdentifier("grape_rose", "drawable", context.packageName), context.resources.newTheme())
                "white" -> context.resources.getDrawable(context.resources.getIdentifier("grape_white", "drawable", context.packageName), context.resources.newTheme())
                else -> context.resources.getDrawable(context.resources.getIdentifier("grape_icon", "drawable", context.packageName), context.resources.newTheme())
            }
            holder.layout.setOnClickListener {
                onClick(wine)
                Toast.makeText(context, "${wine.name}", Toast.LENGTH_SHORT).show()

            }
            holder.btnCart.background = if(cartList.contains(wine.wineId)) context.resources.getDrawable(context.resources.getIdentifier("icon_cart_remove", "drawable", context.packageName), context.resources.newTheme())
            else context.resources.getDrawable(context.resources.getIdentifier("icon_cart_nobg", "drawable", context.packageName), context.resources.newTheme())
            holder.btnCart.setOnClickListener {
                if(onAddCart(wine)){
                    it.background = context.resources.getDrawable(context.resources.getIdentifier("icon_cart_nobg", "drawable", context.packageName), context.resources.newTheme())
                }
                else{
                    it.background = context.resources.getDrawable(context.resources.getIdentifier("icon_cart_remove", "drawable", context.packageName), context.resources.newTheme())
                }
            }
        }
        else{
            holder as GridViewHolder
            Glide.with(holder.itemView.context).load(wine.image).into(holder.imgWine)
            holder.txtWine.text = wine.name!!.capitalize()
            holder.txtPrice.text = "$${wine.price}"
            holder.imgWineColor.background = when(wine.color){
                "red" -> context.resources.getDrawable(context.resources.getIdentifier("tag_normal", "drawable", context.packageName), context.resources.newTheme())
                "rose" -> context.resources.getDrawable(context.resources.getIdentifier("tag_rose", "drawable", context.packageName), context.resources.newTheme())
                "white" -> context.resources.getDrawable(context.resources.getIdentifier("tag_white", "drawable", context.packageName), context.resources.newTheme())
                else -> context.resources.getDrawable(context.resources.getIdentifier("tag_normal", "drawable", context.packageName), context.resources.newTheme())
            }
            holder.layout.setOnClickListener {
                onClick(wine)

            }
            holder.btnCart.background = if(cartList.contains(wine.wineId)) context.resources.getDrawable(context.resources.getIdentifier("icon_cart_remove", "drawable", context.packageName), context.resources.newTheme())
            else context.resources.getDrawable(context.resources.getIdentifier("icon_cart_nobg", "drawable", context.packageName), context.resources.newTheme())
            holder.btnCart.setOnClickListener {
                if(onAddCart(wine)){
                    it.background = context.resources.getDrawable(context.resources.getIdentifier("icon_cart_nobg", "drawable", context.packageName), context.resources.newTheme())
                }
                else{
                    it.background = context.resources.getDrawable(context.resources.getIdentifier("icon_cart_remove", "drawable", context.packageName), context.resources.newTheme())
                }
            }

        }
    }



    fun setWineListItems(wineList: List<WineFeed>){
        this.winesList = wineList
        differ.submitList(this.winesList)
    }


    class LinearViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val layout: ConstraintLayout = itemView!!.findViewById(R.id.constraintElementWine)
        val imgWine: ImageView = itemView!!.findViewById(R.id.imgElementWine)
        val txtWine: TextView = itemView!!.findViewById(R.id.txtElementWineName)
        val imgWineColor: ImageView = itemView!!.findViewById(R.id.imgElementWineColor)
        val btnCart: ImageButton = itemView!!.findViewById(R.id.btnElementWineCart)
        val txtPrice: TextView = itemView!!.findViewById(R.id.txtElementWinePrice)
    }

    class GridViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val layout: ConstraintLayout = itemView!!.findViewById(R.id.constraintWineImage)
        val imgWine: ImageView = itemView!!.findViewById(R.id.imgWineImage)
        val txtWine: TextView = itemView!!.findViewById(R.id.txtWineImageName)
        val imgWineColor: ImageView = itemView!!.findViewById(R.id.imgWineImageColor)
        val btnCart: ImageButton = itemView!!.findViewById(R.id.btnWineImageCart)
        val txtPrice: TextView = itemView!!.findViewById(R.id.txtWineImagePrice)
    }
}