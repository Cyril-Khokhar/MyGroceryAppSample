package com.example.app1gsondata.adapters


import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.models.Cart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_button.view.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class AdapterCart (private var myContext: Context) : RecyclerView.Adapter<AdapterCart.MyViewHolder>(){

    var mylist: ArrayList<Cart> = ArrayList()
    var listener: OnAdapterInteraction? = null

    interface OnAdapterInteraction{
        fun itemViewOnClick(view: View, position: Int, cartItem: Cart)
    }

    fun OnInterfaceImplementation(onAdapterInteraction : OnAdapterInteraction){
        listener = onAdapterInteraction
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(cartItem : Cart, position: Int){
            itemView.text_view_name.text = cartItem.name
            itemView.text_view_price.text = "₹"+cartItem.price.toString()
            itemView.text_view_mrp.text = "was ₹"+cartItem.mrp
            itemView.text_view_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_quantity.text = cartItem.quantity.toString()

            Picasso.get()
                .load(Config.IMAGE_URL+cartItem.image)
                .placeholder(R.drawable.biryani_placeholder)
                .error(R.drawable.biryani_placeholder)
                .into(itemView.image_view_cartItem)

            itemView.button_remove.setOnClickListener {
               listener?.itemViewOnClick(it, position, cartItem)


            }

            itemView.minus_button.setOnClickListener {
                listener?.itemViewOnClick(it, position, cartItem)
            }

            itemView.plus_button.setOnClickListener {
                listener?.itemViewOnClick(it, position, cartItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.MyViewHolder {
        val view = LayoutInflater.from(myContext).inflate(R.layout.row_cart_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mylist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cartItem = mylist[position]
        holder.bindView(cartItem, position)
    }

    fun setData(list : ArrayList<Cart>){
        mylist = list
        notifyDataSetChanged()
    }


}