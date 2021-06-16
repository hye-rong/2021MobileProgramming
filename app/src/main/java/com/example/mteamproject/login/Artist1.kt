package com.example.mteamproject.login

data class Artist1(var aId:String, var aName:String, var likeNum:Int, var soldNum:Int, var loadNum:Int, var aSpec:String){
    constructor():this("noinfo","noinfo", 0, 0, 0,"noinfo")
}
