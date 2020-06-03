package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.helper.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_icon.view.*

class ProfileActivity : AppCompatActivity() {
    var textViewCartIconCount : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mytoolbar()
        init()
    }

    private fun init() {
        var url = "https://www.blakes.com/getmedia/77698556-01f6-4d47-bc6b-4bcf58f0d3ae/mark-morrison.png.aspx"
        Picasso
            .get()
            .load(url)
            .placeholder(R.drawable.imagenotfound)
            .error(R.drawable.imagenotfound)
            .into(profile_image)

        logged_in_email.text = SessionManager().getUser().email
        profile_name.text = "Name : "+SessionManager().getUser().name!!.toUpperCase()
        profile_email.text = "Email : "+SessionManager().getUser().email
        profile_mobile.text = "Mobile : 2184571109"
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
                finish()
            }

            R.id.menu_cart->{
                startActivity(Intent(this, CartActivity::class.java))
            }
            R.id.menu_account->{
                Toast.makeText(applicationContext, "Already in Account", Toast.LENGTH_SHORT).show()
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

    private fun mytoolbar(){
        val mtoolbar = toolbar
        mtoolbar.title = "Account"
        setSupportActionBar(mtoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
