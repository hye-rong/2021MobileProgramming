package com.example.mteamproject.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mteamproject.R
import com.example.mteamproject.databinding.FragmentMPMainBinding

class MPMainFragment : Fragment() {
    lateinit var binding: FragmentMPMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMPMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
    }
    private fun initBtn(){
        binding.apply {
            likeBtn.setOnClickListener {
                (activity as MyPageActivity).changeFragment(1)
            }
            soldBtn.setOnClickListener {
                (activity as MyPageActivity).changeFragment(2)
            }
            loadBtn.setOnClickListener {
                (activity as MyPageActivity).changeFragment(3)
            }
            registerArtistBtn.setOnClickListener {
                //작가 등록 Activity로 이동
            }
            uploadBtn.setOnClickListener {
                //작품 등록 Activity로 이동
            }
        }
    }


}