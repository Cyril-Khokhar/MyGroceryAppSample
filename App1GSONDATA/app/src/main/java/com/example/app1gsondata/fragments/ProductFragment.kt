package com.example.app1gsondata.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.activities.SubCategoryActivity
import com.example.app1gsondata.adapters.AdapterProduct
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.models.Product
import com.example.app1gsondata.models.ProductList
import com.example.app1gsondata.models.SubCategory
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.cart_button.*
import kotlinx.android.synthetic.main.cart_button.view.*
import kotlinx.android.synthetic.main.cart_button.view.cart_quantity
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class ProductFragment : Fragment(), AdapterProduct.OnAdapterInteraction {
    private var subId: Int = 1
    var list : ArrayList<Product> = ArrayList()
    lateinit var myadapter : AdapterProduct
    var view1 : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(SubCategory.SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myadapter = AdapterProduct(activity!!.applicationContext)
        myadapter.OnInterfaceImplementation2(this)
        var view = inflater.inflate(R.layout.fragment_product, container, false)
        var subId = subId
        getProductsBySubId(subId)

        view.recycler_view.adapter = myadapter
        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        return view
    }

    private fun getProductsBySubId(subId : Int){
        var stringRequest = StringRequest(Request.Method.GET, EndPoint.getProductBySubId(subId),
            Response.Listener {
                var gson = GsonBuilder().create()
                var products = gson.fromJson(it.toString(), ProductList::class.java)
                list = products.data
                myadapter.setData(list)

            }, Response.ErrorListener {

            })
        Volley.newRequestQueue(activity).add(stringRequest)
    }

    companion object {

        @JvmStatic
        fun newInstance(subId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(SubCategory.SUB_ID, subId)
                }
            }
    }

    override fun itemViewOnClick(view: View, itemView : View,position: Int, product: Product) {

        when(view.id) {
            R.id.button_add_to_cart -> {
                var quantity = cartdb().findItem(product._id)
                if (quantity == 0) {
                    quantity = 1
                    cartdb().addItems(
                        product._id,
                        product.productName,
                        product.price,
                        product.mrp,
                        quantity,
                        product.image
                    )
                    itemView.button_add_to_cart.visibility = View.GONE
                    itemView.cart_quantity.visibility = View.VISIBLE
                    activity!!.invalidateOptionsMenu()
                }
            }
            R.id.minus_button -> {
                var quantity = itemView.text_view_quantity.text.toString().toInt()
                if (quantity == 1) {
                    cartdb().deleteItem(product._id)
                    itemView.cart_quantity.visibility = View.GONE
                    itemView.button_add_to_cart.visibility = View.VISIBLE
                    activity!!.invalidateOptionsMenu()

                } else {
                    quantity -= 1
                    cartdb().updateQuantity(quantity, product._id)
                    itemView.text_view_quantity.text = quantity.toString()
                    activity!!.invalidateOptionsMenu()
                }


//            var sharedpref = activity!!.getSharedPreferences("cart_count", Context.MODE_PRIVATE)
//            var editor = sharedpref.edit()
//            editor.putInt("count", quantity)
//            editor.commit()
                //var intent = Intent(activity, SubCategoryActivity::class.java)
//            var intent = Intent("CartCount").putExtra("count", quantity)
                // intent.putExtra("count", quantity)
                //activity!!.startActivity(intent)
//            LocalBroadcastManager.getInstance(activity!!).sendBroadcast(intent);


            }
            R.id.plus_button->{
                var quantity = itemView.text_view_quantity.text.toString().toInt()
                quantity+=1
                cartdb().updateQuantity(quantity, product._id)
                itemView.text_view_quantity.text = quantity.toString()
                activity!!.invalidateOptionsMenu()
            }
        }

    }


}
