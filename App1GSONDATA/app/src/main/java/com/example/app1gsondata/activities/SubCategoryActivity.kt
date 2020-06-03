package com.example.app1gsondata.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterFragment
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Category
import com.example.app1gsondata.models.SubCategory
import com.example.app1gsondata.models.SubCategoryList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_icon.view.*


class SubCategoryActivity : AppCompatActivity(){
    private var list : ArrayList<SubCategory> = ArrayList()
    private lateinit var adapterFragment : AdapterFragment
    private var catId = 0
    var textViewCartIconCount : TextView? = null
    var quantity : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        catId = intent.getIntExtra(Category.Cat_ID_KEY, 1)
        adapterFragment = AdapterFragment(supportFragmentManager)
        getCategoryByCatId(catId)
        toolbar()
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
                startActivity(Intent(this, ProfileActivity::class.java))
               // Toast.makeText(applicationContext, "Account", Toast.LENGTH_SHORT).show()
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
    private fun toolbar(){
        val toolbar = toolbar
        toolbar.title = "Sub Categories"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun getCategoryByCatId(catId : Int){
        val stringRequest = StringRequest(Request.Method.GET, EndPoint.getSubCategoryByCatId(catId),
            Response.Listener {
                val gson = GsonBuilder().create()
                val subCategoryData = gson.fromJson(it.toString(), SubCategoryList::class.java)
                list = subCategoryData.data
                for(i in 0 until list.size){
                    adapterFragment.addFragment(list[i])
                }
                view_pager.adapter = adapterFragment
                tab_layout.setupWithViewPager(view_pager)
            }, Response.ErrorListener {

            })
        Volley.newRequestQueue(this).add(stringRequest)
    }

    override fun onRestart() {
        super.onRestart()
        updateCartIconCount()
    }




}
