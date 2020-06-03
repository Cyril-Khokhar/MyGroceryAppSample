package com.example.app1gsondata.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.activities.OrderDetailsActivity
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.models.Order
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_order_adapter.view.*
import kotlinx.android.synthetic.main.row_orderproduct_adapter.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.Inflater

class AdapterOrder(var mContext : Context) : RecyclerView.Adapter<AdapterOrder.MyViewHolder>(){
    var mlist : ArrayList<Order> = ArrayList()
    //val date = LocalDateTime.parse(LocalDateTime.now().toString(),formatter).toString()
    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(order: Order, position: Int){
            var order_num = position+1
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            itemView.text_view_order_number.text = order_num.toString()
            itemView.total_items.text = order.date.substring(0,10).format(formatter)
            itemView.amount_paid.text = order.orderSummary.totalAmount.toString()

//            itemView.button_details.setOnClickListener {
////                itemView.text_view_order_item_name.text = order.products[position].name
////                Picasso.get()
////                    .load(Config.IMAGE_URL+order.products[position].image)
////                    .placeholder(R.drawable.ic_launcher_foreground)
////                    .error(R.drawable.ic_launcher_foreground)
////                    .into(itemView.image_view_order_image)
////                itemView.relative_layout_order_items.visibility = View.VISIBLE
//                var intent = Intent(mContext, OrderDetailsActivity::class.java)
//                intent.putExtra(Order.order_key, order)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                mContext.startActivity(intent)
//            }
            itemView.button_details.setOnClickListener {
                Log.d("order", order.products.toString())
                var intent = Intent(mContext, OrderDetailsActivity::class.java)
                intent.putExtra(Order.order_key, order)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(intent)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var order = mlist[position]
        holder.bindView(order, position)
    }

    fun setData(mylist : ArrayList<Order>){
        mlist = mylist
        notifyDataSetChanged()
    }
}