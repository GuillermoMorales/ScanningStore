package com.storemaker.scanstore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.storemaker.scanstore.R
import com.storemaker.scanstore.network.models.Product
import kotlinx.android.synthetic.main.item_product_view_holder.view.*
import kotlinx.android.synthetic.main.item_view_holder.view.tv_product_name

abstract class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    //List of the items
    private var productList: List<Product> = emptyList()
    //Firebase ref
    private val storage = Firebase.storage

    //Abstract fun that will be filled in the summoner class
    abstract fun setClickListener(itemView: View, product: Product)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_view_holder, parent, false)
        return ViewHolder(view)

    }
    //Returning item list size
    override fun getItemCount(): Int = productList.size

    //Function for updating the items in the list
    fun updateList(products: List<Product>) {
        this.productList = products
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) =
        holder.bind(productList[position])

    //Class for defining  and the desired behavior for each view
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.tv_product_name
        fun bind(product: Product){
            itemView.setOnClickListener{setClickListener(itemView,product)}
            name.text = product.name
            storage.reference.child("photos/"+product.id+".png").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).fit().centerCrop().into(itemView.iv_item_product)
            }.addOnFailureListener {
                Log.e("CUSTOM",it.message!!)
            }
        }
    }
}