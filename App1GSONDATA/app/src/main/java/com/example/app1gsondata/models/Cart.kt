package com.example.app1gsondata.models

import java.io.Serializable

data class Cart(

    var Id : String?=null,
    var name : String?=null,
    var price : Double,
    var mrp : Double,
    var quantity : Int,
    var image : String
): Serializable {}