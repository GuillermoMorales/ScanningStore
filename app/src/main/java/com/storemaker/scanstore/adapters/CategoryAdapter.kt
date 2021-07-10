package com.storemaker.scanstore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.storemaker.scanstore.R
import com.storemaker.scanstore.network.models.Category
import kotlinx.android.synthetic.main.item_view_holder.view.*

abstract class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    //List of the items
    private var categoryList: List<Category> = emptyList()

    //Abstract fun that will be filled in the summoner class
    abstract fun setClickListener(itemView: View, category: Category)


    //Returning viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_holder, parent, false)
        return ViewHolder(view)
    }

    //Returning item list size
    override fun getItemCount(): Int = categoryList.size

    //Function for updating the items in the list
    fun updateList(categories: List<Category>) {
        this.categoryList = categories
        notifyDataSetChanged()
    }

    //Function to define behavior to the viewHolder
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) =
        holder.bind(categoryList[position])

    //Class for defining  and the desired behavior for each view
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.tv_product_name
        fun bind(category: Category){
            itemView.setOnClickListener{setClickListener(itemView,category)}
            name.text = category.name
            /*storage.reference.child("photos/"+product.id+".png").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).fit().centerCrop().into(itemView.iv_item_product)
            }.addOnFailureListener {
                Log.e("CUSTOM",it.message!!)
            }*/
        }
    }
}