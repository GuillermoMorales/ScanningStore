package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.storemaker.scanstore.R
import com.storemaker.scanstore.network.models.Users
import com.storemaker.scanstore.viewmodels.ClientCartDetailViewModel
import kotlinx.android.synthetic.main.fragment_client_cart_detail.*


class ClientCartDetailFragment : Fragment() {
    private val storage = Firebase.storage
    private val clientCartDetailFragmentArgs: ClientCartDetailFragmentArgs by navArgs()
    private lateinit var clientCartDetailViewModel: ClientCartDetailViewModel
    private lateinit var cartId: String
    private lateinit var user: Users


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientCartDetailViewModel =
            ViewModelProvider(this).get(ClientCartDetailViewModel::class.java)
        cartId = clientCartDetailFragmentArgs.cartId
        clientCartDetailViewModel.getCartById(cartId)
        clientCartDetailViewModel.cartFound.observe(this,{
            if(it!=null){
                til_name.editText!!.text = Editable.Factory.getInstance().newEditable(it.product.name)
                til_description.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.product.description)
                til_quantity.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.quantity.toString())
                til_price.editText!!.text = Editable.Factory.getInstance().newEditable(it.product.price)
                til_category.editText!!.text =
                    Editable.Factory.getInstance().newEditable(it.product.category.name)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_cart_detail, container, false)
    }


}