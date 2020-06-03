package com.example.app1gsondata.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.models.Cart
import com.example.app1gsondata.models.Order
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_adapter.view.*
import kotlinx.android.synthetic.main.row_order_adapter.view.*
import kotlinx.android.synthetic.main.row_orderproduct_adapter.view.*

class AdapterOrderProduct (var mContext : Context, var mylist : ArrayList<Order>) : BaseAdapter(){

//    var listener : onAdapterInterface? = null
//
//    interface onAdapterInterface{
//        fun onButtonClick(view:View, position: Int, order: Order)
//    }
//
//    fun onAdapterInteraction(onadapterInterface : onAdapterInterface){
//        listener = onadapterInterface
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_orderproduct_adapter, parent, false)
        var order = mylist[position]
        view.text_view_order_item_name.text = order.products[position].name
        Picasso.get()
            .load(Config.IMAGE_URL+order.products[position].image)
            .placeholder(R.drawable.biryani_placeholder)
            .error(R.drawable.biryani_placeholder)
            .into(view.image_view_order_image)

//        view.id.button_details.setOnClickListener {
//            listener?.onButtonClick(view, position, order)
//        }


        return view
    }

    override fun getItem(position: Int): Any {
        return mylist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mylist.size
    }

}