package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.R
import com.storemaker.scanstore.adapters.CartAdapter
import com.storemaker.scanstore.network.models.Cart
import com.storemaker.scanstore.viewmodels.ClientCartViewModel
import kotlinx.android.synthetic.main.fragment_client_cart.*

class ClientCartFragment : Fragment() {

    private lateinit var clientCartViewModel: ClientCartViewModel
    private lateinit var cartAdapter: CartAdapter
    private var cartListForTotal: MutableList<Cart> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clientCartViewModel = ViewModelProvider(this).get(ClientCartViewModel::class.java)
        cartAdapter = object : CartAdapter() {

            override fun setClickListener(itemView: View, cart: Cart) {
                val nextAction =
                    ClientCartFragmentDirections.actionDestinationClientCartToClientCartDetailFragment(
                        cart.id
                    )
                Navigation.findNavController(itemView).navigate(nextAction)
            }
        }
        clientCartViewModel.cartsList.observe(this, { carts ->
            if (carts != null && carts.isNotEmpty())
                cartAdapter.updateList(carts)
            carts.forEach {
                cartListForTotal.add(it)

            }
            var total = 0.0

            for (cart in cartListForTotal) {
                val price: Double = cart.product.price.toDouble()
                total += price
            }
            if(cartListForTotal.isNotEmpty())
                cartListForTotal.clear()
            til_total.editText!!.text = Editable.Factory.getInstance().newEditable(total.toString())
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clientCartViewModel.getCartByUser(Constants.currentUser!!.id)



        rv_cart.apply {
            setHasFixedSize(true)
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}