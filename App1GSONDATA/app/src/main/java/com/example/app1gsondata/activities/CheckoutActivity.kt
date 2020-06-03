package com.example.app1gsondata.activities

import android.app.Notification.PRIORITY_DEFAULT
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.textclassifier.ConversationActions
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterCart
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckoutActivity : AppCompatActivity() {

    var checkout = cartdb().getcheckoutTotals()
    private val channel_Id = "order_confirmation"
    private val notification_Id = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        toolbar()
        val takeaway = intent.getIntExtra("takeaway", 0)
        if (takeaway == 1) {
            button_pay_on_delivery.text = "Pay at Store"
        }
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
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

    private fun toolbar() {
        val toolbar = toolbar
        toolbar.title = "Checkout"

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {

        text_view_total_items.text = checkout.quantity.toString()
        text_view_total_amount.text = "₹" + checkout.totalMRP.toString()
        //text_view_total_amount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        var price = "%.2f".format(checkout.totalAmount)
        total_price_text.text = "₹" + price
        var discount = "%.2f".format(checkout.totalDiscount)
        discount_text.text = "₹" + discount
        pay_amount_text.text = "₹" + price

        button_pay_on_delivery.setOnClickListener {
            saveOrders()
            var intent = Intent(this, ThankyouActivity::class.java)
            startActivity(intent)
            createOrderConfirmaton()
        }

        button_pay_online.setOnClickListener {
            var intent = Intent(this, PayOnlineActivity::class.java)
            startActivity(intent)

        }
    }

    private fun createOrderConfirmaton() {
        createNotificationChannel()

        val builder = androidx.core.app.NotificationCompat.Builder(this, channel_Id)
        builder.setSmallIcon(R.drawable.chilli)
            .setContentTitle("Order Confirmation")
            .setContentText("Dear User,Thanks for your order")
            .setAutoCancel(true)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
        var notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notification_Id, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Order Confirmaton"
            val description = "Order has been confirmed from Mirchi 360 app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channel_Id, name, importance)
            notificationChannel.description = description
            var notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveOrders() {
        var date = LocalDate.now().toString()

        var address = ""
        var userAddress =
            intent.getSerializableExtra(ShippingAddress.KEY_USER_ADDRESS) as ShippingAddress
        address += userAddress.houseNo + " " + userAddress.streetName + " " + userAddress.city + " " + userAddress.location + " " + userAddress.pincode.toString()

        cartdb().addToOrderSummary(
            userAddress.name,
            address,
            checkout.quantity,
            checkout.totalMRP,
            checkout.totalAmount,
            checkout.totalDiscount
        )

        var userId = SessionManager().getUserId().toString()
        var username = SessionManager().getName().toString()
        var orderSummary = Summary(
            username,
            address,
            checkout.quantity,
            checkout.totalMRP,
            checkout.totalAmount,
            checkout.totalDiscount
        )
        var list = cartdb().getItems()
        // Toast.makeText(this, list.toString(), Toast.LENGTH_LONG).show()
        // var user = User("Alex", "alex@gmail.com", "123456", "1112223334")
        var orderHistory = Order(
            null,
            "Confirmed",
            orderSummary,
            list,
            userAddress,
            SessionManager().getUser(),
            userId,
            date,
            null,
            null
        )


//        var orderHashMap = HashMap<String, String>()
//        orderHashMap.put("_id", userId)
//        orderHashMap.put("orderStatus", "Confirmed")
//        orderHashMap.put("orderSummary", orderSummary.toString())
//        orderHashMap.put("products", list.toString())
//        orderHashMap.put("shippingAddress", address)
//        orderHashMap.put("user", user.toString())
//        orderHashMap.put("userId", userId)
//        orderHashMap.put("date", "05/26/2020")
//        orderHashMap.put("__v", "0")

        //var orderJSONObject = JSONObject(orderHashMap as Map<*,*>)
        var orderJSONObject = JSONObject(Gson().toJson(orderHistory))

        var jsonRequest = JsonObjectRequest(Request.Method.POST, EndPoint.getOrderURL(),
            orderJSONObject, Response.Listener {
                Toast.makeText(this, "Order Saved", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(jsonRequest)

    }


}
