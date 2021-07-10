package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.R
import com.storemaker.scanstore.network.models.Users
import com.storemaker.scanstore.viewmodels.ClientProductInfoViewModel
import kotlinx.android.synthetic.main.fragment_client_product_info.*


class ClientProductInfo : Fragment() {
    private val storage = Firebase.storage
    private val productInfoFragmentArgs: ClientProductInfoArgs by navArgs()
    private lateinit var productViewModel: ClientProductInfoViewModel
    private lateinit var productId: String
    private lateinit var user: Users


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this).get(ClientProductInfoViewModel::class.java)
        productId = productInfoFragmentArgs.productId
        productViewModel.retrieveProduct(productId)
        productViewModel.retrieveUser(Constants.currentUser!!.email, "sdffd")
        productViewModel.productFound.observe(this, {
            if (it != null) {

                til_name.editText!!.text = Editable.Factory.getInstance().newEditable(it.name)
                til_description.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.description)
                til_quantity.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.quantity.toString())
                til_price.editText!!.text = Editable.Factory.getInstance().newEditable(it.price)
                til_category.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.category.name)

                storage.reference.child("photos/" + it.id + ".png").downloadUrl.addOnSuccessListener { uri ->
                    Picasso.get().load(uri).fit().centerCrop().into(iv_photo)
                }.addOnFailureListener { err ->
                    Log.e("CUSTOM", err.message!!)
                }
            }
        })
        productViewModel.userFromSingleQuery.observe(this, {
            if (it != null) {
                user = it
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_product_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_save_product.setOnClickListener {
            productViewModel.saveCart(
                user.id,
                productId,
                Integer.parseInt(til_quantity.editText!!.text.toString()),

                )
        }
    }


}