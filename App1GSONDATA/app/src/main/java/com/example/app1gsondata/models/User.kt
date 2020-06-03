package com.example.app1gsondata.models

import java.io.Serializable

data class User(
    var name: String? = null,
    var email: String,
    var password: String,
    var mobile: String? = null
): Serializable {
    companion object{
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_USERID = "user_id"
        const val KEY_MOBILE = "mobile"

    }
}