package com.example.app1gsondata.app

import android.app.Application

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance : MyApplication
        private set
    }
}