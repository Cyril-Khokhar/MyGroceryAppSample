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
import androidx.fragment.app.Fragment
import com.example.app1gsondata.R
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.fragments.HomeFragment
import com.example.app1gsondata.fragments.ProfileFragment
import com.example.app1gsondata.helper.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_icon.view.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var fragment = Fragment()
    var textViewCartIconCount : TextView? = null

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

    override fun onRestart() {
        super.onRestart()
        updateCartIconCount()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar()
        init()
    }


    private fun toolbar(){
        val toolbar = toolbar
        toolbar.title = "Categories"
        setSupportActionBar(toolbar)
    }


    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()
        bottom_navbar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.navbar_home->{
                fragment = HomeFragment()
                loadFragment(fragment)
            }
            R.id.navbar_search->{

               startActivity(Intent(this, SearchActivity::class.java))

            }

            R.id.navbar_profile->{
//                fragment = ProfileFragment()
////                loadFragment(fragment)
////                toolbar.title = "User Profile"
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}
