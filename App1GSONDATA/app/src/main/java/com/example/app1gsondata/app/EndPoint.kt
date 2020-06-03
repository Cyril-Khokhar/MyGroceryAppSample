package com.example.app1gsondata.app

import com.example.app1gsondata.helper.SessionManager
import com.example.app1gsondata.models.Category

class EndPoint {
    companion object{
        private const val URL_CATEGORY = "category/"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB_ID = "products/sub/"
        private const val URL_REGISTER = "auth/register/"
        private const val URL_LOGIN = "auth/login/"

        fun getCategoryURL() : String{
            return Config.BASE_URL+ URL_CATEGORY
        }

        fun getSubCategoryByCatId(catId : Int) : String{
            return "${Config.BASE_URL+ URL_SUB_CATEGORY+catId}"
        }

        fun getProductBySubId(subId : Int) : String{
            return "${Config.BASE_URL+ URL_PRODUCT_BY_SUB_ID+subId}"
        }

        fun getRegisterURL():String{
            return "${Config.AUTH_URL+ URL_REGISTER}"
        }

        fun getLoginURL():String{
            return "${Config.AUTH_URL+ URL_LOGIN}"
        }

        fun getAddressURL():String{
            return "${Config.ADDRESS_URL}"
        }

        fun getUserAddressURL(): String{
            return "${Config.ADDRESS_URL}+ ${SessionManager().getUserId() as String}/"
        }

        fun getOrderURL(): String{
            return "${Config.ORDER_URL}"
        }

        fun getOrderByUserId(): String{
            return "${Config.ORDER_URL}${SessionManager().getUserId() as String}/"
        }
    }
}