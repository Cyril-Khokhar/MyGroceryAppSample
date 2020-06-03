package com.example.app1gsondata.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app1gsondata.app.Config
import com.example.app1gsondata.app.MyApplication
import com.example.app1gsondata.models.Cart
import com.example.app1gsondata.models.CheckoutTotal
import com.example.app1gsondata.models.Summary
import kotlin.collections.ArrayList

class cartdb() : SQLiteOpenHelper(MyApplication.instance, Config.DATABASE_NAME, null, Config.DATABASE_VERSION){

    private var database : SQLiteDatabase = writableDatabase

    companion object{

        const val TABLE_NAME = "CartItem"
        const val COLUMN_ID = "Id"
        const val COLUMN_ITEM_ID = "ItemId"
        const val COLUMN_NAME = "Name"
        const val COLUMN_PRICE = "Price"
        const val COLUMN_MRP = "MRP"
        const val COLUMN_QUANTITY = "Quantity"
        const val COLUMN_IMAGE = "Image"

        const val TABLE_ORDER_SUMMARY = "OrderSummary"
        const val COL_TOTAL_ITEMS = "TotalItems"
        const val COL_TOTAL_AMOUNT = "TotalAmount"
        const val COL_USER_NAME = "Username"
        const val COL_TOTAL_MRP = "Mrp"
        const val COL_DISCOUNT = "Discount"
        const val COl_ADDRESS = "Address"
    }


    override fun onCreate(db: SQLiteDatabase?) {

        val CART_TABLE = "CREATE TABLE ${TABLE_NAME}(${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${COLUMN_ITEM_ID} VARCHAR(20), ${COLUMN_NAME} VARCHAR(50), ${COLUMN_PRICE} DOUBLE,${COLUMN_MRP} DOUBLE, ${COLUMN_QUANTITY} INTEGER, ${COLUMN_IMAGE} VARCHAR(200))"
        val ORDER_SUMMARY = "CREATE TABLE ${TABLE_ORDER_SUMMARY}(${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${COL_USER_NAME} VARCHAR(50), ${COl_ADDRESS} VARCHAR(100), ${COL_TOTAL_ITEMS} INTEGER, ${COL_TOTAL_MRP} DOUBLE, ${COL_TOTAL_AMOUNT} DOUBLE,${COL_DISCOUNT} DOUBLE)"
        db?.execSQL(CART_TABLE)
        db?.execSQL(ORDER_SUMMARY)
    }

    fun deleteItem(Id: String){
        val whereClause = "${COLUMN_ITEM_ID}=?"
        val whereArgs = arrayOf(Id)
        database.delete(TABLE_NAME, whereClause,whereArgs)
    }

    fun addItems(Id: String, name : String, price : Double,mrp: Double, quantity : Int, image: String){
        val contentValues = ContentValues()

        contentValues.put(COLUMN_ITEM_ID, Id)
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_PRICE, price)
        contentValues.put(COLUMN_MRP, mrp)
        contentValues.put(COLUMN_QUANTITY, quantity)
        contentValues.put(COLUMN_IMAGE, image)
        database.insert(TABLE_NAME, null, contentValues)
    }

    fun getItems() : ArrayList<Cart>{
        val itemList : ArrayList<Cart> = ArrayList()
        val columns = arrayOf(COLUMN_ITEM_ID, COLUMN_NAME, COLUMN_PRICE,
            COLUMN_MRP, COLUMN_QUANTITY, COLUMN_IMAGE)
        val cursor = database.query(TABLE_NAME, columns,null,null,null,null,null)

        if(cursor!=null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))

               var cart = Cart(id, name, price,mrp, quantity,image)
                itemList.add(cart)

            }while(cursor.moveToNext())
        }
        cursor.close()
        return itemList


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun findItem(Id: String) : Int{
        var quantity = 0
        val find_query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ITEM_ID=?"
        val cursor = database.rawQuery(find_query, arrayOf(Id))
        if(cursor!=null && cursor.moveToFirst()){
             quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
        }
        cursor.close()
        return quantity
    }

    fun updateQuantity(quantity: Int, Id: String){
        val where_clause = "$COLUMN_ITEM_ID=?"
        val where_args = arrayOf(Id)
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, quantity)
        database.update(TABLE_NAME,contentValues, where_clause, where_args)
    }

    fun addToOrderSummary(username : String, address : String, totalItems : Int, totalMRP : Double, totalAmount : Double, totalDiscount : Double){
        val contentValues = ContentValues()
        contentValues.put(COL_USER_NAME, username)
        contentValues.put(COl_ADDRESS, address)
        contentValues.put(COL_TOTAL_ITEMS, totalItems)
        contentValues.put(COL_TOTAL_MRP, totalMRP)
        contentValues.put(COL_TOTAL_AMOUNT, totalAmount)
        contentValues.put(COL_DISCOUNT, totalDiscount)

        database.insert(TABLE_ORDER_SUMMARY, null, contentValues)
    }

    fun getOrderSummary() : Summary {
        var username = ""
        var address = ""
        var totalItems = 0
        var totalMRP = 0.0
        var totalAmount = 0.0
        var totalDiscount = 0.0

        val columns = arrayOf(COL_USER_NAME, COl_ADDRESS, COL_TOTAL_ITEMS, COL_TOTAL_MRP, COL_TOTAL_AMOUNT, COL_DISCOUNT)
        val cursor = database.query(TABLE_ORDER_SUMMARY, columns, null,null,null,null,null)
        if(cursor!=null && cursor.moveToFirst()){
             username = cursor.getString(cursor.getColumnIndex(COL_USER_NAME))
             address = cursor.getString(cursor.getColumnIndex(COl_ADDRESS))
             totalItems = cursor.getInt(cursor.getColumnIndex(COL_TOTAL_ITEMS))
             totalMRP = cursor.getDouble(cursor.getColumnIndex(COL_TOTAL_MRP))
             totalAmount = cursor.getDouble(cursor.getColumnIndex(COL_TOTAL_AMOUNT))
             totalDiscount = cursor.getDouble(cursor.getColumnIndex(COL_DISCOUNT))
        }
        cursor.close()
        return Summary(username, address,totalItems, totalMRP, totalAmount, totalDiscount)
    }

    fun getcheckoutTotals() : CheckoutTotal {
        var quantity = 0
        var mrps = 0.0
        var amount = 0.0
        var discount = 0.0


        val column = arrayOf(COLUMN_QUANTITY, COLUMN_MRP, COLUMN_PRICE)
        val cursor = database.query(TABLE_NAME, column, null,null,null,null,null)
        if(cursor!=null && cursor.moveToFirst()){
            do{
                quantity += cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                mrps += cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))
                amount += cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
            }
                while(cursor.moveToNext())
        }
        cursor.close()
        discount = mrps-amount
        var checkoutTotal = CheckoutTotal(quantity, mrps, amount, discount)
        return checkoutTotal
    }

    fun dropTables(){
        var query = "DROP TABLE $TABLE_NAME"
        database.execSQL(query)
    }

    fun deleteData() {
        database.delete(TABLE_NAME, null, null)
    }
}