package com.example.mteamproject.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mteamproject.R
import com.example.mteamproject.databinding.FragmentMpLikeBinding
import com.google.android.gms.common.util.ArrayUtils


class MPLikeFragment : Fragment() {
    lateinit var binding:FragmentMpLikeBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var likeList:ArrayList<Product>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMpLikeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    private fun initData(){
        //ViewModel로 할지 이렇게 할지 다시 생각해서 결정
        myDBHelper = MyDBHelper(requireContext())
        likeList = myDBHelper.getAllRecord(0)
    }
}