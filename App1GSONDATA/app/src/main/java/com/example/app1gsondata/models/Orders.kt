package com.example.app1gsondata.models

import java.io.Serializable

data class Summary(
    var username : String,
    var address : String,
    var totalItems : Int,
    var totalMRP : Double,
    var totalAmount : Double,
    var totalDiscount : Double
) : Serializable

data class Order(
    var _id : String?,
    var orderStatus : String,
    var orderSummary : Summary,
    var products : ArrayList<Cart>,
    var shippingAddress : ShippingAddress,
    var users : User,
    var userId : String,
    var date : String,
    var __v : Int? = null,
    var payment : Payment?
) : Serializable{
    companion object{
        const val order_key = "order"
    }
}

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val location: String,
    val mobile: String,
    val name: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
): Serializable{
    companion object{
        val KEY_USER_ADDRESS = "useraddresskey"
    }
}


data class Payment(
    var Id: String,
    var paymentMode: String,
    var paymentStatus: String
) : Serializable

data class OrderList(
    var data : ArrayList<Order>
)

//data class Order(
////    val __v: Int? = null,
////    val _id: String? = null,
////    val date: String? = null,
////    val orderStatus: String? = null,
////    val orderSummary: Summary? = null,
////    val payment: Payment? = null,
////    val products: ArrayList<Product>? = null,
////    val shippingAddress: ShippingAddress,
////    val users: Users,
////    val userId: String
////)
////
////data class Summary(
////    var _id: String? = null,
////    var deliveryCharges: Int,
////    var discount: Double,
////    var orderAmount: Int? = null,
////    var ourPrice: Double? = null,
////    var totalAmount:Int? = null
////)
////
////data class Payment(
////    val _id: String,
////    val paymentMode: String,
////    val paymentStatus: String
////)
////
////data class Product(
////    var _id: String,
////    var image: String,
////    var mrp: Double,
////    var price: Double,
////    var quantity: Int
////)
////
////data class ShippingAddress(
////    var city: String,
////    var houseNo: String,
////    var pincode: Int,
////    var streetName: String
////)
////
////data class Users(
////    val _id: String,
////    val email: String,
////    val mobile: String,
////    val name: String
////)