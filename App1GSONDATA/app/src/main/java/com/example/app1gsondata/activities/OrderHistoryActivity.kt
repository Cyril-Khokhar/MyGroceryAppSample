package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterOrder
import com.example.app1gsondata.adapters.AdapterOrderProduct
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Order
import com.example.app1gsondata.models.OrderList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.activity_thankyou.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_order_adapter.*

class OrderHistoryActivity : AppCompatActivity(){

    var mlist : ArrayList<Order> = ArrayList()
    lateinit var myadapter : AdapterOrder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        getOrders()
        toolbarMenu()
        myadapter = AdapterOrder(this)
        display()
        //myadapter2 = AdapterOrderProduct(this, mlist )
//        myadapter2.onAdapterInteraction(this)

//       var btn = findViewById<Button>(R.id.button_details)
//        btn.setOnClickListener {
//            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
//        }
        //text_view_username.text = Order().orderSummary.totalAmount.toString()
    }

    private fun display() {
        recycler_view_order_history.adapter = myadapter
        recycler_view_order_history.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
    }

    private fun toolbarMenu() {
        var toolBar = toolbar
        toolBar.title = "Order History"
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

    private fun getOrders() {
        val request = JsonObjectRequest(Request.Method.GET, EndPoint.getOrderByUserId(),null,
            Response.Listener {
                var gson = GsonBuilder().create()
                var orderlist = gson.fromJson(it.toString(), OrderList::class.java)
                mlist = orderlist.data
                myadapter.setData(mlist)
                Log.d("orders", mlist.toString())

            }, Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(this).add(request)
    }

//    override fun onButtonClick(view: View, position: Int, order: Order) {
////
////        listView.adapter = myadapter2
////        relative_layout_order_items.visibility = View.VISIBLE
////    }
}
