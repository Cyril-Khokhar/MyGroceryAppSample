package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {

        click_to_register.setOnClickListener {
            finish()
            startActivity(Intent(this, NewRegisterActivity::class.java))
        }

        button_login1.setOnClickListener {
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()
            var user = User(email=email, password = password)
            loginUser(user)
//            var sessionManager = SessionManager()
//            if (sessionManager.login(user)) {
//                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, MainActivity::class.java))
//            } else {
//                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    fun loginUser(user: User){
        var params = HashMap<String, String>()
        var user_id = ""
        var name = ""
        params.put("email", user.email)
        params.put("password", user.password)

        var jsonObject = JSONObject(params as Map<*,*>)

        var request = JsonObjectRequest(Request.Method.POST, EndPoint.getLoginURL(),
            jsonObject, Response.Listener {
                Toast.makeText(applicationContext,"Logged in Successfully", Toast.LENGTH_SHORT).show()
                var jsonObject = it.getJSONObject("user")
                user_id = jsonObject.getString("_id")
                name = jsonObject.getString("firstName")
               // Toast.makeText(applicationContext,it.toString(), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                SessionManager().register(user)
                SessionManager().saveUser(user_id,name)

                finish()
            }, Response.ErrorListener {
                Toast.makeText(applicationContext,"Invalid email or password", Toast.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(this).add(request)
    }
}
