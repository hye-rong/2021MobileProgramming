package com.example.mteamproject

import android.graphics.Bitmap

data class Artist(var name:String,var icon:Bitmap?){
    constructor():this("",null)
}

