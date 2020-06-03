package com.example.app1gsondata.models

data class Category(
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val status: Boolean
){
    companion object{
        const val Cat_ID_KEY = "catId"
    }
}