package com.storemaker.scanstore.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.widget.ListPopupWindow
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.storemaker.scanstore.R
import com.storemaker.scanstore.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_info.*
import kotlinx.android.synthetic.main.fragment_product_info.til_name
import java.io.ByteArrayOutputStream


class ProductInfoFragment : Fragment() {

    private val storage = Firebase.storage
    private val productInfoFragmentArgs: ProductInfoFragmentArgs by navArgs()

    private lateinit var productViewModel: ProductViewModel
    private lateinit var listPopupWindow: ListPopupWindow
    private var categorySelected: String = ""
    private lateinit var productId: String
    private val categoriesStrings = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productId = productInfoFragmentArgs.productId
        if (productId != "1") {
            productViewModel.retrieveProduct(productId)
            productViewModel.productFound.observe(this, {
                if (it != null) {

                    til_name.editText!!.text = Editable.Factory.getInstance().newEditable(it.name)
                    til_description.editText!!.text =
                        Editable.Factory.getInstance().newEditable(it.description)
                    til_quantity.editText!!.text =
                        Editable.Factory.getInstance().newEditable(it.quantity.toString())
                    til_price.editText!!.text = Editable.Factory.getInstance().newEditable(it.price)

                    storage.reference.child("photos/" + it.id + ".png").downloadUrl.addOnSuccessListener { uri ->
                        Picasso.get().load(uri).fit().centerCrop().into(iv_photo)
                    }.addOnFailureListener { err ->
                        Log.e("CUSTOM", err.message!!)
                    }
                }
            })
            productViewModel.productUpdated.observe(this, {product->
                if (product != null) {
                    try {
                        val width = resources.configuration.screenWidthDp
                        val height = resources.configuration.screenHeightDp
                        val smallerDimension = if (width < height) width else height
                        val qrgEncoder =
                            QRGEncoder(product.id, null, QRGContents.Type.TEXT, smallerDimension)
                        qrgEncoder.colorBlack = Color.WHITE
                        qrgEncoder.colorWhite = Color.BLACK
                        try {
                            // Getting QR-Code as Bitmap
                            val bitmap = qrgEncoder.bitmap
                            // Setting Bitmap to ImageView
                            iv_qr.setImageBitmap(bitmap)
                            uploadImageFromImageViewToFirebase(iv_qr, product.name, "${product.id}.png")
                            if (iv_photo.drawable != null) {
                                uploadImageFromImageViewToFirebase(iv_photo, product.name, "photos/${product.id}.png")

                            }
                        } catch (e: Exception) {
                            Log.e("CUSTOM", e.toString())
                        }
                        Toast.makeText(
                            requireContext(),
                            "Product updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }
        productViewModel.retrieveCategories()
        productViewModel.categoryList.observe(this, {
            if (it != null) {
                it.forEach { category ->
                    categoriesStrings.add(category.name)
                }

                // Set list popup's content
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_popup_window_item,
                    categoriesStrings
                )
                listPopupWindow.setAdapter(adapter)
            }
        })
        productViewModel.productInsertedId.observe(this, {
            if (it != null && it != "") {
//                Log.d("CUSTOM", "Changed $it")
                val width = resources.configuration.screenWidthDp
                val height = resources.configuration.screenHeightDp
                val smallerDimension = if (width < height) width else height
                val qrgEncoder =
                    QRGEncoder(it, null, QRGContents.Type.TEXT, smallerDimension)
                qrgEncoder.colorBlack = Color.WHITE
                qrgEncoder.colorWhite = Color.BLACK
                try {
                    // Getting QR-Code as Bitmap
                    val bitmap = qrgEncoder.bitmap
                    // Setting Bitmap to ImageView
                    iv_qr.setImageBitmap(bitmap)
                    uploadImageFromImageViewToFirebase(iv_qr, it, "${it}.png")
                    if (iv_photo.drawable != null) {
                        uploadImageFromImageViewToFirebase(iv_photo, it, "photos/${it}.png")

                    }
                } catch (e: Exception) {
                    Log.e("CUSTOM", e.toString())
                }
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_select_image.setOnClickListener { openGalleryIntent() }

        listPopupWindow = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)

        // Set button as the list popup's anchor
        listPopupWindow.anchorView = list_popup_button


        // Set list popup's item click listener
        listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            // Respond to list popup window item click.
            categorySelected = categoriesStrings.elementAt(position)
            // Dismiss popup.
            listPopupWindow.dismiss()
        }
        // Show list popup window on button click.
        list_popup_button.setOnClickListener { v: View? -> listPopupWindow.show() }

        bt_save_product.setOnClickListener {
            if (!areInputsNotEmpty()) {
                if (productId == "1") {
                    productViewModel.saveProduct(
                        til_name.editText!!.text.toString(),
                        til_description.editText!!.text.toString(),
                        Integer.parseInt(til_quantity.editText!!.text.toString()),
                        til_price.editText!!.text.toString(),
                        categorySelected
                    )
                } else {
                    productViewModel.updateProduct(
                        productId,
                        til_name.editText!!.text.toString(),
                        til_description.editText!!.text.toString(),
                        Integer.parseInt(til_quantity.editText!!.text.toString()),
                        til_price.editText!!.text.toString(),
                        categorySelected
                    )
                }
            } else {
                try {
                    Toast.makeText(context, "Please fill all the imputs", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_info, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) fillImageViewFromIntent(data!!)

    }


    private fun openGalleryIntent() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    private fun areInputsNotEmpty(): Boolean =
        til_name.editText!!.text.isEmpty() && til_description.editText!!.text.isEmpty() && til_quantity.editText!!.text.isEmpty()

    //Function for uploading the image contained in imageView to FireBase Cloud Storage
    private fun uploadImageFromImageViewToFirebase(
        imageView: ImageView,
        productId: String,
        pathString: String
    ) {
        val photoRef = storage.reference.child(pathString)
        val uploadBitMap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        uploadBitMap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val myData = baos.toByteArray()
        val uploadTask = photoRef.putBytes(myData)
        uploadTask.addOnSuccessListener {
            try {
                Toast.makeText(context, it.metadata.toString(), Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.addOnFailureListener {
            Log.e("CUSTOM", it.message!!)
        }
    }

    //Function for filling the imageView from the selected picture
    private fun fillImageViewFromIntent(data: Intent) {
        val uri = data.data!!
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        iv_photo.isDrawingCacheEnabled = true
        iv_photo.buildDrawingCache()
        iv_photo.setImageBitmap(bitmap)
    }
}