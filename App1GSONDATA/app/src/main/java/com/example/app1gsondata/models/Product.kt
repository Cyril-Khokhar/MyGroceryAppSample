package com.example.app1gsondata.models

import java.io.Serializable

data class Product(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Double,
    val position: Int,
    val price: Double,
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
): Serializable{
    companion object{
        const val PRODUCT_KEY = "productId"
    }
}
