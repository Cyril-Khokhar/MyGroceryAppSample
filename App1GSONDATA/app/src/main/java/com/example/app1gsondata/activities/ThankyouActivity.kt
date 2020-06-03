package com.example.app1gsondata.activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterCart
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.database.cartdb
import com.example.app1gsondata.helper.SessionManager
import kotlinx.android.synthetic.main.activity_thankyou.*

class ThankyouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)
        init()
    }

    private fun init() {
        text_view_username.text = SessionManager().getName()
        var handler = Handler()
        handler.postDelayed({
            clearDataAndGotoMain()
        },5000)
    }

    private fun clearDataAndGotoMain() {
        AdapterCart(this).mylist.clear()
//        this.deleteDatabase(Config.DATABASE_NAME)
        cartdb().deleteData()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
