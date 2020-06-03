package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app1gsondata.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pay_online.*

class PayOnlineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_online)
        Picasso.get().load("https://i.ya-webdesign.com/images/payment-logos-png.png").into(image_view_card)
        init()

    }

    private fun init() {
        button_pay_now.setOnClickListener {
            var name = edit_text_name_on_card.text.toString()
            var cardNumber = edit_text_card_number.text.toString()
            var cardDate = date_picker.text.toString()
            var code = security_code.text.toString()
            if(name.isNotBlank() && cardNumber.isNotBlank() && cardDate.isNotBlank() && code.isNotBlank()){
                startActivity(Intent(this, ThankyouActivity::class.java))
                Toast.makeText(applicationContext, "Payment Successful", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "Please check your information", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
