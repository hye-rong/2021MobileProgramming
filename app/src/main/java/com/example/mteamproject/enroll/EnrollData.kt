package com.example.mteamproject.enroll

import android.net.Uri

data class EnrollData(var userID: String,
                      var title: String,
                      var imageUrl: String,
                      var genre:String,
                      var sellPrice: Int,
                      var ifauction: Boolean,
                      var endDate : List<Int>,
                      var nowDate : String
                      )