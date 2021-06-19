package com.example.mteamproject.login

data class User(var uId:String, var uPassword:String, var uName:String, var uAge:Int, var Artist:Boolean, var likeNum:Int, var soldNum:Int, var loadNum:Int, var aSpec:String, var zImgUrl:String){
    constructor():this("noinfo","noinfo", "noinfo", 0, false, 0, 0, 0, "noinfo", "noinfo")
}