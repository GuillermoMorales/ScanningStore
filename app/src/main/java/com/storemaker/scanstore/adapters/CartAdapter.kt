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
import com.storemaker.scanstore.network.models.Cart
import com.storemaker.scanstore.network.models.Product
import kotlinx.android.synthetic.main.item_product_view_holder.view.*

abstract class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    //List of the items
    private var cartList: List<Cart> = emptyList()
    //Firebase ref
    private val storage = Firebase.storage

    //Abstract fun that will be filled in the summoner class
    abstract fun setClickListener(itemView: View, cart: Cart)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_view_holder, parent, false)
        return ViewHolder(view)

    }
    //Returning item list size
    override fun getItemCount(): Int = cartList.size

    //Function for updating the items in the list
    fun updateList(carts: List<Cart>) {
        this.cartList = carts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) =
        holder.bind(cartList[position])
    //Class for defining  and the desired behavior for each view
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.tv_product_name
        fun bind(cart: Cart){
            name.text = cart.product.name
            itemView.setOnClickListener{setClickListener(itemView,cart)}

            storage.reference.child("photos/"+cart.product.id+".png").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).fit().centerCrop().into(itemView.iv_item_product)
            }.addOnFailureListener {
                Log.e("CUSTOM",it.message!!)
            }
        }
    }
}