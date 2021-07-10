package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.storemaker.scanstore.R
import com.storemaker.scanstore.adapters.ProductAdapter
import com.storemaker.scanstore.network.models.Product
import com.storemaker.scanstore.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*


class ProductListFragment : Fragment() {


    private lateinit var productAdapter:ProductAdapter
    private lateinit var productViewModel:ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = object :ProductAdapter(){
            override fun setClickListener(itemView: View, product: Product) {
                val nextAction = ProductListFragmentDirections.actionDestinationProductListToProductInfoFragment()
                nextAction.productId = product.id
                Navigation.findNavController(itemView).navigate(nextAction)
            }
        }
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.productList.observe(this,{products->
            if(products!= null && products.isNotEmpty())
                productAdapter.updateList(products)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productViewModel.retrieveProducts()
        rv_product_list.apply {

            setHasFixedSize(true)
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)

    }


}