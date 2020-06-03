package com.example.app1gsondata.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.app1gsondata.R

import com.example.app1gsondata.app.MyApplication
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*

fun Context.toast(text : String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

//fun Snackbar.snackbarShow(text:String){
//    var snackbar = Snackbar.make(, text, Snackbar.LENGTH_SHORT)
//    snackbar.show()
//}