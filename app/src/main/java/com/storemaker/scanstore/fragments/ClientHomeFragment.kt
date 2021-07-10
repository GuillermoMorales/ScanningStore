package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.storemaker.scanstore.R
import com.storemaker.scanstore.adapters.ProductAdapter
import com.storemaker.scanstore.network.models.Product
import com.storemaker.scanstore.viewmodels.ClientProductViewModel
import kotlinx.android.synthetic.main.fragment_client_home.*


class ClientHomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var clientProductViewModel:ClientProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = object :ProductAdapter(){
            override fun setClickListener(itemView: View, product: Product) {
                val nextAction = ClientHomeFragmentDirections.actionDestinationClientHomeToClientProductInfo(product.id)
                Navigation.findNavController(itemView).navigate(nextAction)
/*                val nextAction = ClientHomeFragmentDirections.actionDestinationClientHomeToDestinationClientProductInfo(product.id)
                Navigation.findNavController(itemView).navigate(nextAction)*/
            }
        }
        clientProductViewModel = ViewModelProvider(this).get(ClientProductViewModel::class.java)
        clientProductViewModel.productList.observe(this,{products->
            if(products!= null && products.isNotEmpty())
                productAdapter.updateList(products)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clientProductViewModel.retrieveProducts()
        rv_client_product_list.apply{
            setHasFixedSize(true)
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_menu,menu)
    }*/
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)

    }*/

}