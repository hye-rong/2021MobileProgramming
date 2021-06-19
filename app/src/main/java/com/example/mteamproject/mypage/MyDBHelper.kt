package com.example.mteamproject.mypage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log

class MyDBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "mydb.db"
        val DB_VERSION = 6
        val FAVORITE_TABLE_NAME = "favorites"
        val PURCHASE_TABLE_NAME = "purchases"
        val COIN_TABLE_NAME = "coin"
        val AUCTION_TABLE_NAME = "auction"
        val PID = "pid"
        val PAUTHOR = "pauthor"
        val PPIC = "ppic"
        val PPRICE = "pprice"
        val PCOIN = "pcoin"
        val PKEY = "pkey"
    }
    fun getAllRecord(i:Int):MutableList<Product>{
        // 0: 즐겨찾기 1: 구매목록
        val pList = mutableListOf<Product>()
        val strsql = if(i==0){
            "select * from $FAVORITE_TABLE_NAME;"
        } else {
            "select * from $PURCHASE_TABLE_NAME;"
        }


        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        Log.d("EOEOEO", "getAllRecord :${i}번째 0:like, 1:sold => ${cursor.count}개 존재")

        if (cursor.count == 0) return pList

        do {
            pList.add(
                Product(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return pList
    }

    fun getAuction():MutableList<Product>{
        val pList = mutableListOf<Product>()
        val strsql = "select * from $AUCTION_TABLE_NAME;"

        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0) return pList

        do {
            pList.add(
                Product(
                    cursor.getString(0),
                    cursor.getString(3),
                    cursor.getString(1),
                    cursor.getInt(2)
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return pList
    }

    fun getCoin():Int{
        val pList = mutableListOf<Product>()
        val strsql = "select * from $COIN_TABLE_NAME;"

        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        Log.d("EOEOEO", "getCoin : ${cursor.count}원")
        if (cursor.count == 0){
            val values = ContentValues()
            values.put(PID, "coin")
            values.put(PCOIN, 0)
            db.insert(COIN_TABLE_NAME, null, values)
            return 0
        }
        else{
            cursor.moveToFirst()
            return cursor.getInt(1)
        }
    }
    fun insertCoin(c:Int){
        val pid = "coin"
        val strsql = "select * from $COIN_TABLE_NAME where $PID='$pid'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(PCOIN, c)
            db.update(COIN_TABLE_NAME, values,"$PID=?", arrayOf(pid))
        }
        cursor.close()
        db.close()
    }
    fun insertFavoriteProduct(product: Product){
        Log.d("EOEOEO", "insertFavoriteProduct: $product")
        val values = ContentValues()
        values.put(PID, product.pId)
        values.put(PAUTHOR, product.pAuthor)
        values.put(PPIC, product.pPic)
        values.put(PPRICE, product.pPrice)
        val db = writableDatabase
        val flag = db.insert(FAVORITE_TABLE_NAME, null, values)
        Log.d("EOEOEO","flag: $flag")
        db.close()
    }
    fun insertPurchaseProduct(product: Product){
        val values = ContentValues()
        values.put(PID, product.pId)
        values.put(PAUTHOR, product.pAuthor)
        values.put(PPIC, product.pPic)
        values.put(PPRICE, product.pPrice)
        val db = writableDatabase
        db.insert(PURCHASE_TABLE_NAME, null, values)
        db.close()
    }
    fun insertAuctionProduct(id:String, img:String, price:Int, key:String){
        val values = ContentValues()
        values.put(PID, id)
        values.put(PPIC, img)
        values.put(PPRICE, price)
        values.put(PKEY, key)
        val db = writableDatabase
        db.insert(AUCTION_TABLE_NAME, null, values)
        db.close()
    }
    fun deleteFavoriteProduct(pid:String){
        val strsql = "select * from $FAVORITE_TABLE_NAME where $PID='$pid'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            db.delete(FAVORITE_TABLE_NAME, "$PID=?", arrayOf(pid))
        }
        cursor.close()
        db.close()

    }
    fun deleteAuction(pid:String){
        val strsql = "select * from $AUCTION_TABLE_NAME where $PID='$pid'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            db.delete(FAVORITE_TABLE_NAME, "$PID=?", arrayOf(pid))
        }
        cursor.close()
        db.close()
    }

    fun findFavorite(pid:String):Boolean{
        val strsql = "select * from $FAVORITE_TABLE_NAME where $PID='$pid'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        return cursor.count != 0

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val create_ftable = "create table if not exists $FAVORITE_TABLE_NAME("+
                "$PID text, "+
                "$PAUTHOR text, "+
                "$PPIC text, "+
                "$PPRICE integer);"
        val create_ptable = "create table if not exists $PURCHASE_TABLE_NAME("+
                "$PID text, "+
                "$PAUTHOR text, "+
                "$PPIC text, "+
                "$PPRICE integer);"
        val create_ctable = "create table if not exists $COIN_TABLE_NAME("+
                "$PID text, "+
                "$PCOIN integer);"
        val create_atable = "create table if not exists $AUCTION_TABLE_NAME("+
                "$PID text, " +
                "$PPIC text, " +
                "$PPRICE integer, " +
                "$PKEY text);"


        db!!.execSQL(create_ftable)
        db!!.execSQL(create_ptable)
        db!!.execSQL(create_ctable)
        db!!.execSQL(create_atable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_ftable = "drop table if exists $FAVORITE_TABLE_NAME;"
        val drop_ptable = "drop table if exists $PURCHASE_TABLE_NAME;"
        val drop_ctable = "drop table if exists $COIN_TABLE_NAME;"
        val drop_atable = "drop table if exists $AUCTION_TABLE_NAME;"

        db!!.execSQL(drop_ftable)
        db!!.execSQL(drop_ptable)
        db!!.execSQL(drop_ctable)
        db!!.execSQL(drop_atable)
        onCreate(db)
    }

}