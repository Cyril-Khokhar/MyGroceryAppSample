package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app1gsondata.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        init()
    }

    private fun init() {
        button_register.setOnClickListener {
            startActivity(Intent(this, NewRegisterActivity::class.java))
        }

        button_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


}
