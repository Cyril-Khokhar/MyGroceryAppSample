package com.example.app1gsondata.models

data class CheckoutTotal(
    var quantity : Int,
    var totalMRP : Double,
    var totalAmount : Double,
    var totalDiscount : Double
)