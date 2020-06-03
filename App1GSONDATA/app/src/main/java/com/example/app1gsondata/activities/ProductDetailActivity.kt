package com.example.app1gsondata.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app1gsondata.R
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.fragments.HomeFragment
import com.example.app1gsondata.fragments.ProfileFragment
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*

class ProductDetailActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        toolbar.title = "Product Details"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
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
                Toast.makeText(applicationContext, "cart", Toast.LENGTH_SHORT).show()
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


        var product = intent.getSerializableExtra(Product.PRODUCT_KEY) as Product
        Picasso
            .get()
            .load(Config.IMAGE_URL+product.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(image_view_detail)

        text_view_name_detail.text = product.productName
        text_view_price_detail.text = "₹"+product.price.toString()
        text_view_description.text = product.description
        if(product.status){
            text_view_status.text = "Available right now!"
        }
        else{
            text_view_status.text = "Not available"
        }
    }
}
