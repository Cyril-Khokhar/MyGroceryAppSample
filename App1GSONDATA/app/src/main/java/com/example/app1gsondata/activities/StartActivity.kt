package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.app1gsondata.R
import com.example.app1gsondata.helper.SessionManager

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed({
            checkUser()
        },3000)
    }

    private fun checkUser() {
        var intent = if(SessionManager().isLoggedIn()){
            //Intent(this, MainActivity::class.java)
            Intent(this, MainActivity::class.java)
        }
        else{
            Intent(this, UserActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
