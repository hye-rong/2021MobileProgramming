package com.example.mteamproject.login

data class User(var uId:String, var uPassword:String, var uName:String, var uAge:Int, var Artist:Boolean){
    constructor():this("noinfo","noinfo", "noinfo", 0, false)
}