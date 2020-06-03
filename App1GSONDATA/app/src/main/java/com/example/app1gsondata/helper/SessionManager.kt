package com.example.app1gsondata.helper

import android.content.Context
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.app.MyApplication
import com.example.app1gsondata.models.User

class SessionManager {
    private var sharePreference = MyApplication.instance.getSharedPreferences(Config.FILE_NAME, Context.MODE_PRIVATE)
    private var primaryAddressDATA = MyApplication.instance.getSharedPreferences("PrimaryAddress", Context.MODE_PRIVATE)
    private var editor = sharePreference.edit()
    private var editor_address = primaryAddressDATA.edit()

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun getUserId() : String?{
        return sharePreference.getString(User.KEY_USERID, "")
    }

    fun getName() : String?{
        return sharePreference.getString(User.KEY_NAME, "")
    }

    fun saveUser(user_id : String, username : String){
        editor.putString(User.KEY_USERID, user_id)
        editor.putString(User.KEY_NAME, username)
        editor.commit()
    }

    fun savePrimaryAddress(addressType : String, houseNum : String, street : String, city : String, state : String, zipcode : String){
        editor_address.putString("addressType", addressType)
        editor_address.putString("houseNum", houseNum)
        editor_address.putString("street", street)
        editor_address.putString("city", city)
        editor_address.putString("state", state)
        editor_address.putString("zipcode", zipcode)
        editor_address.commit()

    }

    fun getPrimaryAddress() : HashMap<String, String>{

        var primaryAddress = HashMap<String, String>()
        primaryAddress.put("addressType", primaryAddressDATA.getString("addressType", "")!!)
        primaryAddress.put("houseNum", primaryAddressDATA.getString("houseNum", "")!!)
        primaryAddress.put("street", primaryAddressDATA.getString("street", "")!!)
        primaryAddress.put("city", primaryAddressDATA.getString("city", "")!!)
        primaryAddress.put("state", primaryAddressDATA.getString("state", "")!!)
        primaryAddress.put("zipcode", primaryAddressDATA.getString("zipcode", "")!!)
        return primaryAddress
    }




    fun register(user: User) {
        editor.putString(User.KEY_NAME, user.name)
        editor.putString(User.KEY_EMAIL, user.email)
        editor.putString(User.KEY_PASSWORD, user.password)
        editor.putString(User.KEY_MOBILE, user.mobile)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }

    fun login(user: User): Boolean {
        var saveEmail = sharePreference.getString(User.KEY_EMAIL, null)
        var savePassword = sharePreference.getString(User.KEY_PASSWORD, null)
        return user.email.equals(saveEmail) && user.password.equals(savePassword)
    }



    fun getUser(): User{
       var name = sharePreference.getString(User.KEY_NAME, "")
        var email = sharePreference.getString(User.KEY_EMAIL, "")
       var password = sharePreference.getString(User.KEY_PASSWORD, "")
        var mobile = sharePreference.getString(User.KEY_MOBILE, "")
       var user = User(name!!, email!!, password!!, mobile!!)
        return user
    }

    fun logout(){
        //editor.remove(KEY_NAME)
        editor.clear()
        editor.commit()
        editor_address.clear()
        editor_address.commit()
    }

    fun isLoggedIn(): Boolean{
        return sharePreference.getBoolean(KEY_IS_LOGGED_IN, false)
    }

}