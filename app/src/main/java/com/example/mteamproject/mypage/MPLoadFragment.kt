package com.example.mteamproject.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.R
import com.example.mteamproject.databinding.FragmentMpLoadBinding

class MPLoadFragment : Fragment() {
    lateinit var binding:FragmentMpLoadBinding
    lateinit var loadList:MutableList<Product>
    lateinit var adapter: LikesAdapter
    lateinit var myDBHelper: MyDBHelper
    val mpViewModel : MPViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMpLoadBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    private fun initData(){

        loadList = mpViewModel.loadsLiveData.value!!
        adapter = LikesAdapter(loadList)

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerView.adapter = adapter
        }

    }

}