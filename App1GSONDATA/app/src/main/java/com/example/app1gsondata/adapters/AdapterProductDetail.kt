package com.example.app1gsondata.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Cart
import com.example.app1gsondata.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_order_details.view.*
import kotlinx.android.synthetic.main.row_order_details_adapter.view.*
import kotlinx.android.synthetic.main.row_orderproduct_adapter.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterProductDetail (var mContext: Context): RecyclerView.Adapter<AdapterProductDetail.MyViewHolder>(){
    var mlist : ArrayList<Cart> = ArrayList()
    var primaryAddress = SessionManager().getPrimaryAddress()
    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(product: Cart) {

            itemView.text_view_product_name.text = ""
            itemView.text_view_product_id.text = "5ec7ed8ba3ca7d0017c87"
            itemView.text_view_product_quantity.text = product.quantity.toString()
            itemView.text_view_product_price.text = "₹"+product.price.toString()
            //itemView.shipped_address.text = "${primaryAddress["adddressType"]} # ${primaryAddress["houseNum"]},${primaryAddress["street"]},${primaryAddress["city"]},${primaryAddress["zipcode"]}"

            Picasso
                .get()
                .load(Config.IMAGE_URL+product.image)
                .placeholder(R.drawable.biryani_placeholder)
                .error(R.drawable.biryani_placeholder)
                .into(itemView.image_view_order_detail)
        }
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_details_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    fun setData(list : ArrayList<Cart>){
        mlist = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AdapterProductDetail.MyViewHolder, position: Int) {
        var product = mlist[position]
        holder.bindView(product)
    }
}