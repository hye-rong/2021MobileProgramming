package com.example.mteamproject.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.R
import com.example.mteamproject.databinding.FragmentMpLikeBinding



class MPLikeFragment : Fragment() {
    lateinit var binding:FragmentMpLikeBinding
    lateinit var likeList:MutableList<Product>
    lateinit var adapter: LikesAdapter
    lateinit var myDBHelper: MyDBHelper
    val mpViewModel : MPViewModel by activityViewModels()

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
        myDBHelper = MyDBHelper(requireContext())
        likeList = mpViewModel.likesLiveData.value!!
        adapter = LikesAdapter(likeList)

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerView.adapter = adapter
        }
        adapter.itemClickListener = object : LikesAdapter.OnItemClickListener{
            override fun OnItemClick(holder: RecyclerView.ViewHolder, data: Product, pos: Int) {
                //작품 상세 페이지로 이동
                //작품명으로 firebase에서 작품 정보를 받아와서 넘겨줘야할듯

            }

            override fun OnLikeClick(holder: RecyclerView.ViewHolder, data: Product, pos: Int) {
                adapter.items.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                mpViewModel.likesLiveData.value!!.removeAt(pos)
                myDBHelper.deleteFavoriteProduct(data.pId)
            }
        }
    }
}