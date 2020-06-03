package com.example.app1gsondata.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.activities.SubCategoryActivity
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.models.Category
import com.example.app1gsondata.models.Product
import com.example.app1gsondata.models.SubCategory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCategory (var myContext : Context) : RecyclerView.Adapter<AdapterCategory.MyViewHolder>(){
    var list : ArrayList<Category> = ArrayList()
    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(category : Category){
            itemView.text_view_row.text = category.catName

            itemView.setOnClickListener {
                var intent = Intent(myContext, SubCategoryActivity::class.java)
                intent.putExtra(Category.Cat_ID_KEY, category.catId)

                myContext.startActivity(intent)
            }

            Picasso.get()
                .load(Config.IMAGE_URL+category.catImage)
                .placeholder(R.drawable.biryani_placeholder)
                .error(R.drawable.biryani_placeholder)
                .into(itemView.image_view_row)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = list[position]
        holder.bindView(category)
    }

    fun setData(list : ArrayList<Category>){
        this.list = list
        notifyDataSetChanged()
    }
}