package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.ShippingAddress
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddressActivity : AppCompatActivity() {
    var address_type = ""
    lateinit var userAddress: ShippingAddress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
       mytoolbar()
        relative_layout_New_address.visibility = View.GONE
        displayPrimaryAddress()
        hideAndShow()
        init()

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

    private fun checkAddresstype() {
        radio_group_homeType.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio_button_apt->{
                    address_type = "Apartment"
                }
                R.id.radio_button_house->{
                    address_type = "House"
                }
            }
        }
    }

    private fun init() {
        var intent = Intent(this, CheckoutActivity::class.java)
        button_deliver_to_new_address.setOnClickListener {
            addAddress()
            intent.putExtra(ShippingAddress.KEY_USER_ADDRESS, userAddress)
           startActivity(intent)
        }
        button_deliver_to_primary_address.setOnClickListener {

            val name = edit_text_name1.text.toString().trim()
            val houseNum = edit_text_house_number1.text.toString().trim()
            val street = edit_text_street_name1.text.toString().trim()
            val city = edit_text_city1.text.toString().trim()
            val zipcode = edit_text_zipcode1.text.toString().trim()
            val state = edit_text_state1.text.toString().trim()
            val mobile = edit_text_mobile1.text.toString().trim()
            val userId = SessionManager().getUserId() as String

            if(radio_button_house1.isChecked){
                address_type = "House"
            }
            else
                address_type = "Apartment"

            userAddress = ShippingAddress(city, houseNum, state, mobile, name, zipcode.toInt(), street, address_type, userId)
            SessionManager().savePrimaryAddress(address_type, houseNum, street , city, state, zipcode)

            val primaryAddress = HashMap<String, String>()
            primaryAddress.put("name", name)
            primaryAddress.put("houseNo", houseNum)
            primaryAddress.put("streetName", street)
            primaryAddress.put("city", city)
            primaryAddress.put("pincode", zipcode)
            primaryAddress.put("location", state)
            primaryAddress.put("mobile", mobile)
            primaryAddress.put("type", address_type)
            primaryAddress.put("userId", userId)

//            userAddress = ShippingAddress(
//                city, houseNum, state, mobile,
//                SessionManager().getName().toString(), zipcode.toInt(), street, address_type,
//                SessionManager().getUserId().toString()
//            )

//            val address = HashMap<String, String>()
//            address.put("name", userAddress.name)
//            address.put("houseNo", userAddress.houseNo)
//            address.put("streetName", userAddress.streetName)
//            address.put("city", userAddress.city)
//            address.put("pincode", userAddress.pincode.toString())
//            address.put("location", userAddress.location)
//            address.put("mobile", userAddress.mobile)
//            address.put("type", "House")
//            address.put("userId", userAddress.userId)

            val addressJSON = JSONObject(primaryAddress as Map<*, *>)
            networkCallToAddressApi(addressJSON)
            intent.putExtra(ShippingAddress.KEY_USER_ADDRESS, userAddress)
            startActivity(intent)
        }
    }

    private fun displayPrimaryAddress() {

        var primaryAddress = SessionManager().getPrimaryAddress()

        edit_text_name1.setText(SessionManager().getUser().name)
        edit_text_house_number1.setText(primaryAddress["houseNum"])
        edit_text_street_name1.setText(primaryAddress["street"])
        edit_text_city1.setText(primaryAddress["city"])
        edit_text_zipcode1.setText(primaryAddress["zipcode"])
        edit_text_state1.setText(primaryAddress["state"])
        edit_text_mobile1.setText(SessionManager().getUser().mobile)

        address_type = primaryAddress["addressType"]!!
        if (address_type.equals("House")){
            radio_button_house1.isChecked=true
            radio_button_apt1.isChecked = false
        }
        else{
            radio_button_apt1.isChecked = true
            radio_button_house1.isChecked = false
        }

    }

    private fun hideAndShow() {
        radio_group_addressType.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio_button_primary_address->{

                    relative_layout_New_address.visibility = View.GONE
                    relative_layout_primary_address.visibility = View.VISIBLE
                    //button_deliver_to_primary_address.visibility = View.VISIBLE
                }

                R.id.radio_button_new_address->{
                    relative_layout_New_address.visibility = View.VISIBLE
                    relative_layout_primary_address.visibility = View.GONE
                    //button_deliver_to_primary_address.visibility = View.GONE
                }
            }
        }
    }

    fun mytoolbar(){
        var toolbar1 = toolbar
        toolbar1.title = "Add Address "
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun networkCallToAddressApi(address : JSONObject) {
        var jsonRequest = JsonObjectRequest(Request.Method.POST, EndPoint.getAddressURL(),
        address, Response.Listener {
                Toast.makeText(this, "Address Saved", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener {
                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(jsonRequest)
    }

    private fun addAddress() {

        val name = edit_text_name.text.toString()
        val houseNum = edit_text_house_number.text.toString()
        val street = edit_text_street_name.text.toString()
        val city = edit_text_city.text.toString()
        val zipcode = edit_text_zipcode.text.toString()
        val state = edit_text_state.text.toString()
        val mobile = edit_text_mobile.text.toString()
        val userId = SessionManager().getUserId() as String

        userAddress = ShippingAddress(city, houseNum, state, mobile, name, zipcode.toInt(), street, address_type, userId)

        val address = HashMap<String, String>()
        address.put("name", name)
        address.put("houseNo", houseNum)
        address.put("streetName", street)
        address.put("city", city)
        address.put("pincode", zipcode)
        address.put("location", state)
        address.put("mobile", mobile)
        address.put("type", address_type)
        address.put("userId", userId)

            val addressJSON = JSONObject(address as Map<*,*>)
            networkCallToAddressApi(addressJSON)
           // val url = "http://apolis-grocery.herokuapp.com/api/address/5ec7ed8ba3ca7d0017c87cc9"
//            var getAddress = JsonObjectRequest(Request.Method.GET, EndPoint.getUserAddressURL(),null,
//            Response.Listener {
//                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
//
//            }, Response.ErrorListener {
//                    Log.d("address_error", it.message)
//                })




        //            var address = UserAddress(
////                "San Jose", "11",
////                "California", "2013344221",
////                SessionManager().getName().toString(), 112234,
////                "Lanai Avenue", "home", SessionManager().getUserId().toString()
////            )


    }
}
