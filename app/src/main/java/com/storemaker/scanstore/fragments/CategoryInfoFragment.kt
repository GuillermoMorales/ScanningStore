package com.storemaker.scanstore.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.storemaker.scanstore.R
import com.storemaker.scanstore.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_category_info.*
import java.lang.Exception


class CategoryInfoFragment : Fragment() {

    private val categoryFragmentArgs: CategoryInfoFragmentArgs by navArgs()
    private lateinit var mainViewModel:MainViewModel
    private lateinit var categoryId :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        categoryId = categoryFragmentArgs.categoryId
        if(categoryId != "1"){
            mainViewModel.retrieveCategory(categoryId)
            mainViewModel.categoryFound.observe(this,{
                if(it!=null){
                    til_name.editText!!.text = Editable.Factory.getInstance().newEditable(it.name)
                }
            })
            mainViewModel.categoryUpdated.observe(this,{
                if(it!=null){
                    try{
                        Toast.makeText(requireContext(),"Category updated successfully", Toast.LENGTH_SHORT).show()

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
        }else{
            mainViewModel.categoryInsertedId.observe(this,{
                if(it!=null && it.isNotEmpty()){
                    try{
                        Toast.makeText(requireContext(),"Category inserted successfully", Toast.LENGTH_SHORT).show()

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_category.setOnClickListener {
            try{
                when(categoryId){
                    "1"->{
                        val name = til_name.editText!!.text.toString()
                        Log.d("Coso",name)
                        mainViewModel.saveCategory(name)
                    }
                    else->{
                        val name = til_name.editText!!.text.toString()

                        mainViewModel.updateCategory(categoryId,name)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


}