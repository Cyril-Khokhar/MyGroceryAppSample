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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterCart
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.models.Cart
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_icon.view.*

class CartActivity : AppCompatActivity() , AdapterCart.OnAdapterInteraction{

    var cartItemList : ArrayList<Cart> = ArrayList()
    lateinit var myadapter : AdapterCart
    var textViewCartIconCount : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        myadapter = AdapterCart(this)

        myadapter.OnInterfaceImplementation(this)

        toolbar.title = "Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCartItems()
        goHome()
        checkout()
    }


    private fun checkout() {
//        button_checkout.setOnClickListener {
//            finish()
//            startActivity(Intent(this, CheckoutActivity::class.java))
//        }
        button_delivery.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

        button_takeaway.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("takeaway", 1)
            startActivity(intent)
        }
    }

    private fun goHome() {
        button_go_to_home.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun updateUI(){
        cartItemList = cartdb().getItems()
        myadapter.setData(cartItemList)
        myadapter.notifyDataSetChanged()
        updateCartIconCount()
        recycler_view_cart.adapter = myadapter
        recycler_view_cart.layoutManager = LinearLayoutManager(this)


        if(cartItemList.size<1){
            relative_layout_total.visibility = View.GONE
            relative_layout_empty_cart.visibility = View.VISIBLE
        }
        else{
            relative_layout_total.visibility = View.VISIBLE
            relative_layout_empty_cart.visibility = View.GONE
        }

    }

    private fun getCartItems() {
        cartItemList = cartdb().getItems()

        myadapter.setData(cartItemList)
        recycler_view_cart.adapter = myadapter
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        recycler_view_cart.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        if(cartItemList.size<1){
            relative_layout_total.visibility = View.GONE
            relative_layout_empty_cart.visibility = View.VISIBLE
        }
        else{
            relative_layout_total.visibility = View.VISIBLE
            relative_layout_empty_cart.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        var item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_icon)
        var view = MenuItemCompat.getActionView(item)
        textViewCartIconCount = view.text_view_cart_icon
        updateCartIconCount()
        view.setOnClickListener {
            Toast.makeText(this, "Already in cart", Toast.LENGTH_SHORT).show()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{

                var intent = Intent(this, SubCategoryActivity::class.java)
              startActivity(intent)
                finish()
            }
//            R.id.action_cart->{
//                Toast.makeText(this, "You are already in Cart", Toast.LENGTH_SHORT).show()
//            }

        }
        return super.onOptionsItemSelected(item)
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

//    override fun itemViewOnClick(view: View, position: Int, product : Product){
//
//        var quantity = cartdb().findItem(product._id)
//        if(quantity == 0){
//            quantity = 1
//            cartdb().addItems(product._id, product.productName, product.price,product.mrp, quantity, product.image)
//            view.visibility = View.GONE
//        }
//    }


    override fun itemViewOnClick(view: View, position: Int, cartItem: Cart) {
        var quantity = 0
        when (view.id) {
            R.id.button_remove -> {
                cartdb().deleteItem(cartItem.Id!!)
                updateUI()
            }
            R.id.minus_button -> {
                quantity = cartdb().findItem(cartItem.Id!!)
                if (quantity == 1) {
                    cartdb().deleteItem(cartItem.Id!!)
                    updateUI()
                } else {
                    quantity = cartdb().findItem(cartItem.Id!!)
                    cartdb().updateQuantity(quantity - 1, cartItem.Id!!)
                    updateUI()
                }
            }
            R.id.plus_button -> {
                quantity = cartdb().findItem(cartItem.Id!!)
                cartdb().updateQuantity(quantity + 1, cartItem.Id!!)
                updateUI()

            }
        }
    }
}
