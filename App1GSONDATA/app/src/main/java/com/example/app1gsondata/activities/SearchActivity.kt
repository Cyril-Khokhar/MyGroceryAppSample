package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Category
import com.example.app1gsondata.models.CategoryList
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_icon.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class SearchActivity : AppCompatActivity() {

    var categoryList = ArrayList<Category>()
    var textViewCartIconCount : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var gettoolbar = toolbar
        gettoolbar.title = "Search"
        setSupportActionBar(gettoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        text_view_searchitem.text = ""
        init()
    }

    fun updateCartIconCount(){
        var items = cartdb().getcheckoutTotals()
        var quantity = items.quantity
        if(quantity==0){
            textViewCartIconCount?.visibility = View.GONE
        }
        else
            textViewCartIconCount?.visibility = View.VISIBLE
        textViewCartIconCount?.text = quantity.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        var item = menu.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_icon)
        var view = MenuItemCompat.getActionView(item)
        textViewCartIconCount = view.text_view_cart_icon
        updateCartIconCount()
        view.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java ))
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.menu_cart->{
                startActivity(Intent(this, CartActivity::class.java))
            }
            R.id.menu_account->{
                Toast.makeText(applicationContext, "Account", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_order->{
                startActivity(Intent(this, OrderHistoryActivity::class.java))
            }
            R.id.menu_logout->{
                SessionManager().logout()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun networkCall(){

        val stringRequest = StringRequest(Request.Method.GET, EndPoint.getCategoryURL(),
            Response.Listener {

                var gson = GsonBuilder().create()
                var list = gson.fromJson(it.toString(), CategoryList::class.java)
                categoryList = list.data

            } , Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } )
        Volley.newRequestQueue(this).add(stringRequest)

    }

    private fun init() {
        var position = 0
        var found = false
        button_search.setOnClickListener {
            var item = edit_searchbar.text.toString().toLowerCase()
            networkCall()
            for(i in 0 until categoryList.size){
                if(item.equals(categoryList[i].catName.toLowerCase())){
                    position = i
                    found = true
                    break
                }
            }
            if(found){
                Picasso
                    .get()
                    .load(Config.IMAGE_URL+categoryList[position].catImage)
                    .placeholder(R.drawable.imagenotfound)
                    .error(R.drawable.imagenotfound)
                    .into(image_view_searchitem)
                text_view_searchitem.text = categoryList[position].catName

                image_view_searchitem.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            }
            else
                text_view_searchitem.text = "Item Not Found"

        }

    }
}
