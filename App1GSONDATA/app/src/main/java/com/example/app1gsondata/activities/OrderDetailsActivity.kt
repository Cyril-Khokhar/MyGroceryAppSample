package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterOrder
import com.example.app1gsondata.adapters.AdapterProduct
import com.example.app1gsondata.adapters.AdapterProductDetail
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Cart
import com.example.app1gsondata.models.Order
import com.example.app1gsondata.models.OrderList
import com.example.app1gsondata.models.Product
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderDetailsActivity : AppCompatActivity() {

    var mlist : ArrayList<Cart> = ArrayList()
    lateinit var myadapter : AdapterProductDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        toolbarMenu()
        init()
    }
    private fun toolbarMenu() {
        var toolBar = toolbar
        toolBar.title = "Order Details"
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
            R.id.menu_delete_history->{
                if(mlist.isEmpty()){
                    Toast.makeText(applicationContext, "History already Cleared", Toast.LENGTH_SHORT).show()
                }
                else{
                    mlist.clear()
                    myadapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "History Cleared", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.menu_account->{
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_logout->{
                SessionManager().logout()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {

        myadapter = AdapterProductDetail(this)
        getOrders()
        recycler_view_order_details.adapter = myadapter
        recycler_view_order_details.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getOrders() {
                var order = intent.getSerializableExtra(Order.order_key) as Order
                mlist = order.products

                shipped_address.text = "H# ${order.shippingAddress.houseNo},${order.shippingAddress.streetName} ,${order.shippingAddress.city},${SessionManager().getPrimaryAddress()["state"]}, ${order.shippingAddress.pincode}"
                date_ordered.text = order.date.substring(0, 10)
                username.text = SessionManager().getUser().name!!.toUpperCase()

                myadapter.setData(mlist)
                Log.d("orders", mlist.toString())

    }
}
