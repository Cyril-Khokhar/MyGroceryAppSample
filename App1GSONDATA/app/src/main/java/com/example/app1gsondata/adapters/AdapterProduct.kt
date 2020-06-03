package com.example.app1gsondata.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.activities.ProductDetailActivity
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_button.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterProduct (var myContext: Context): RecyclerView.Adapter<AdapterProduct.MyViewHolder>(){
    var mlist : ArrayList<Product> = ArrayList()
    var listener : OnAdapterInteraction? = null
//    var listener : Linker? = null

    interface OnAdapterInteraction{
        fun itemViewOnClick(view: View, itemView: View,position: Int, product: Product)
    }

    fun OnInterfaceImplementation2(onAdapterInteraction : OnAdapterInteraction){
        listener = onAdapterInteraction
    }
    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun showHideAddButtons(product: Product){
            var quantity = cartdb().findItem(product._id)
            if(quantity >= 1){
                itemView.button_add_to_cart.visibility = View.GONE
                itemView.cart_quantity.visibility = View.VISIBLE
            }
            else{
                itemView.button_add_to_cart.visibility = View.VISIBLE
            }
        }

        fun bindView(product : Product, position: Int){
            itemView.text_view_name.text = product.productName
            itemView.text_view_price.text = "₹"+product.price.toString()
            itemView.text_view_mrp.text = "₹"+product.mrp.toString()
            itemView.text_view_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.setOnClickListener {
                var intent = Intent(myContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.PRODUCT_KEY, product)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                myContext.startActivity(intent)
            }

            itemView.button_add_to_cart.setOnClickListener {

  //              var quantity = cartdb().findItem(product._id)
//                if(quantity == 0){
//                    quantity = 1
//                    cartdb().addItems(product._id, product.productName, product.price,product.mrp, quantity, product.image)
//                    itemView.button_add_to_cart.visibility = View.GONE
//                    itemView.cart_quantity.visibility = View.VISIBLE
                    listener?.itemViewOnClick(it,itemView, position, product)
                }

//                var quantity = cartdb().findItem(product._id)
//                if(quantity == 0){
//                    quantity = 1
//                    cartdb().addItems(product._id, product.productName, product.price,product.mrp, quantity, product.image)
//                }
//                else{
//                    quantity++
//                    cartdb().updateQuantity(quantity, product._id)
//                }
//                notifyDataSetChanged()
//                Toast.makeText(myContext, "Item added successfully", Toast.LENGTH_SHORT).show()
   //         }

            itemView.minus_button.setOnClickListener {
                listener?.itemViewOnClick(it, itemView, position, product)
//                var quantity = itemView.text_view_quantity.text.toString().toInt()
//                if(quantity==1){
//                    cartdb().deleteItem(product._id)
//                    itemView.cart_quantity.visibility = View.GONE
//                    itemView.button_add_to_cart.visibility = View.VISIBLE
//
//                }
//                else{
//                    quantity-=1
//                    cartdb().updateQuantity(quantity, product._id)
//                    itemView.text_view_quantity.text = quantity.toString()
//                    listener = listener as? Linker
//                    listener?.updateCartIconFromAdapterProduct()
               // }
            }

            itemView.plus_button.setOnClickListener {
                listener?.itemViewOnClick(it, itemView,position,product)
//                var quantity = itemView.text_view_quantity.text.toString().toInt()
//                quantity+=1
//                cartdb().updateQuantity(quantity, product._id)
//                itemView.text_view_quantity.text = quantity.toString()
//                listener = listener as? Linker
//                listener?.updateCartIconFromAdapterProduct()
            }

            Picasso
                .get()
                .load(Config.IMAGE_URL+product.image)
                .placeholder(R.drawable.biryani_placeholder)
                .error(R.drawable.biryani_placeholder)
                .into(itemView.image_view_product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_product_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mlist[position]
        holder.bindView(product, position)
        holder.showHideAddButtons(product)
    }

    fun setData(list : ArrayList<Product>){
        mlist = list
        notifyDataSetChanged()
    }
}