package com.example.mteamproject.enroll

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EnrollDB(input: EnrollData) {
    lateinit var endb: DatabaseReference
    var input : EnrollData
    init {
        this.input = input
    }

    fun addEnrollDB() {
        val database = FirebaseDatabase.getInstance()
        val Enref = database.getReference()

        var userID = ""
        //userID를 기준으로 그 아래에 EnrollData db 입력

        Enref.child("Art").child("userID").push().setValue(input)
   }

    public fun readEnrollDB() {
        //사용자
        //쓰일지는 모르겟음
    }
}