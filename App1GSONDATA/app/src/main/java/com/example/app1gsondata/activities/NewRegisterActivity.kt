package com.example.app1gsondata.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1gsondata.R
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.helper.toast
import com.example.app1gsondata.models.User
import kotlinx.android.synthetic.main.activity_new_register.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.click_to_login
import kotlinx.android.synthetic.main.activity_register.edit_text_email
import kotlinx.android.synthetic.main.activity_register.edit_text_mobile
import kotlinx.android.synthetic.main.activity_register.edit_text_name
import kotlinx.android.synthetic.main.activity_register.edit_text_password
import kotlinx.android.synthetic.main.activity_register.text_input_layout_email
import kotlinx.android.synthetic.main.activity_register.text_input_layout_mobile
import kotlinx.android.synthetic.main.activity_register.text_input_layout_name
import kotlinx.android.synthetic.main.activity_register.text_input_layout_password
import org.json.JSONObject

class NewRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_register)
        init()
    }

    private fun init() {

        click_to_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        button_new_register.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            var name = edit_text_name.text.toString().trim()
            var email = edit_text_email.text.toString().trim()
            var password = edit_text_password.text.toString().trim()
            var mobile = edit_text_mobile.text.toString().trim()
            var user = User(name, email, password,mobile)

            if(validateForm()){
                Toast.makeText(this, "Validate", Toast.LENGTH_SHORT).show()
                this.toast("Valid")
                registerUser(user)
                Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
            }
            else
                this.toast("Invalid")

            var sessionManager = SessionManager()
            sessionManager.register(user)
           // startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    fun validateForm(): Boolean{
        if(edit_text_name.text.isNullOrBlank()){
            text_input_layout_name.error = "PLease enter your name"
            return false
        }
        if(edit_text_email.text.isNullOrBlank()){
            text_input_layout_email.error = "PLease enter your email"
            return false
        }
        if(edit_text_password.text.isNullOrBlank()){
            text_input_layout_password.error = "PLease enter your password"
            return false
        }
        if(edit_text_mobile.text.isNullOrBlank()){
            text_input_layout_mobile.error = "PLease enter your mobile number"
            return false
        }
        text_input_layout_mobile.isErrorEnabled = false
        text_input_layout_password.isErrorEnabled = false
        text_input_layout_email.isErrorEnabled = false
        text_input_layout_name.isErrorEnabled = false
        return true
    }

    fun registerUser(user : User){
        SessionManager().register(user)
        var params = HashMap<String, String>()
        params["firstName"]= user.name as String
        params["email"] = user.email
        params["password"] = user.password
        params["mobile"] = user.mobile as String

        var jsonObject = JSONObject(params as Map<*,*>)
        var request = JsonObjectRequest(Request.Method.POST, EndPoint.getRegisterURL(),
            jsonObject, Response.Listener {
                Toast.makeText(applicationContext,"Registered Successfully", Toast.LENGTH_SHORT).show()
                SessionManager().register(user)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, Response.ErrorListener {
                Toast.makeText(applicationContext,"Invalid Inputs", Toast.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(this).add(request)
    }
}
