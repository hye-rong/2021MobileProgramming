package com.example.mteamproject.login

import android.net.Uri

data class Product1(var pId:String, var pName:String ,var pAuthor:String, var pPic: Int, var pPrice:Int){
    constructor():this("noinfo","noinfo", "noinfo", 0, 0)
}