package com.example.mteamproject.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.databinding.FragmentMpSoldBinding


class MPSoldFragment : Fragment() {
    lateinit var binding:FragmentMpSoldBinding
    lateinit var soldList:MutableList<Product>
    lateinit var adapter: SoldAdapter
    val mpViewModel : MPViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMpSoldBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    private fun initData(){
        soldList = mpViewModel.soldsLiveData.value!!
        adapter = SoldAdapter(soldList)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }

    }

}