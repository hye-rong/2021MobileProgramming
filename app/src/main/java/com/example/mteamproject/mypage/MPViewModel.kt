package com.example.mteamproject.mypage


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MPViewModel:ViewModel() {
    var likesLiveData = MutableLiveData<MutableList<Product>>()
    var soldsLiveData = MutableLiveData<MutableList<Product>>()
    var loadsLiveData = MutableLiveData<MutableList<Product>>()

}