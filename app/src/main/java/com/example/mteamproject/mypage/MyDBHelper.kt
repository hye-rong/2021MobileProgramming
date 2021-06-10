package com.example.mteamproject.mypage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.net.Uri
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class MyDBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val FAVORITE_TABLE_NAME = "favorites"
        val PURCHASE_TABLE_NAME = "purchases"
        val PID = "pid"
        val PPIC = "ppic"
        val PPRICE = "pprice"
    }
    fun getAllRecord(i:Int):ArrayList<Product>{
        // 0: 즐겨찾기 1: 구매목록
        val pList = arrayListOf<Product>()
        val strsql = if(i==0){
            "select * from $FAVORITE_TABLE_NAME;"
        } else {
            "select * from $PURCHASE_TABLE_NAME;"
        }


        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0) return pList

        do {
            pList.add(
                Product(
                    cursor.getString(0), Uri.parse(cursor.getString(1)),
                    cursor.getInt(2)
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return pList
    }

    fun insertFavoriteProduct(product: Product){
        val values = ContentValues()
        values.put(PID, product.pId)
        values.put(PPIC, product.pPic.toString())
        values.put(PPRICE, product.pPrice)
        val db = writableDatabase
        db.insert(FAVORITE_TABLE_NAME, null, values)
        db.close()
    }
    fun insertPurchaseProduct(product: Product){
        val values = ContentValues()
        values.put(PID, product.pId)
        values.put(PPIC, product.pPic.toString())
        values.put(PPRICE, product.pPrice)
        val db = writableDatabase
        db.insert(PURCHASE_TABLE_NAME, null, values)
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

    override fun onCreate(db: SQLiteDatabase?) {
        val create_ftable = "create table if not exists $FAVORITE_TABLE_NAME("+
                "$PID integer, "+
                "$PPIC text, "+
                "$PPRICE integer);"
        val create_ptable = "create table if not exists $PURCHASE_TABLE_NAME("+
                "$PID integer, "+
                "$PPIC text, "+
                "$PPRICE integer);"
        db!!.execSQL(create_ftable)
        db!!.execSQL(create_ptable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_ftable = "drop table if exists $FAVORITE_TABLE_NAME;"
        val drop_ptable = "drop table if exists $PURCHASE_TABLE_NAME;"
        db!!.execSQL(drop_ftable)
        db!!.execSQL(drop_ptable)
        onCreate(db)
    }

}