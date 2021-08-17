package com.harshit.shoplist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshit.shoplist.R
import com.harshit.shoplist.utils.Products

class ProductListAdapter(private val listener: ProductItemClicked): RecyclerView.Adapter<ProductViewHolder>(){

    private val items: ArrayList<Products> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        val viewHolder = ProductViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.title
        holder.price.text = currentItem.price.toString()
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun updateProducts(updatedProduct: ArrayList<Products>){
        items.clear()
        items.addAll(updatedProduct)
        notifyDataSetChanged()
    }

}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val price: TextView = itemView.findViewById(R.id.price)


}

interface ProductItemClicked{
    fun onItemClicked(item: Products)
}