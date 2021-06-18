package com.example.mteamproject.enroll

import android.content.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EnrollDB(input: EnrollData) {
    lateinit var endb: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    var input : EnrollData
    init {
        this.input = input
    }

    fun addEnrollDB() {
        val database = FirebaseDatabase.getInstance()
        val Enref = database.getReference()




        Enref.child("Art").child(input.userID).push().setValue(input)
   }

    public fun readEnrollDB() {
        //사용자
        //쓰일지는 모르겟음
    }
}