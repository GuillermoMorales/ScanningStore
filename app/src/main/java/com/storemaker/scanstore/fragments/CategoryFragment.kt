package com.storemaker.scanstore.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.storemaker.scanstore.Constants
import com.storemaker.scanstore.MainActivity
import com.storemaker.scanstore.R
import com.storemaker.scanstore.adapters.CategoryAdapter
import com.storemaker.scanstore.network.models.Category
import com.storemaker.scanstore.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter = object :CategoryAdapter(){
            override fun setClickListener(itemView: View, category: Category) {
                val nextAction = CategoryFragmentDirections.actionDestinationCategoryToCategoryInfoFragment()
                nextAction.categoryId = category.id
                Navigation.findNavController(itemView).navigate(nextAction)
            }
        }
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.categoryList.observe(this,  {categories->

            if(categories!= null && categories.isNotEmpty())
                categoryAdapter.updateList(categories)

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.retrieveCategories()
        rv_category_list.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.category_info_options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){
            if(Constants.signInClient!=null){
                Constants.signInClient!!.signOut().addOnCompleteListener {
                    val myIntent = Intent(requireActivity(),MainActivity::class.java)
                    startActivity(myIntent)
                }
            }
            return super.onOptionsItemSelected(item)
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)

    }
    private fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

}