package com.example.mteamproject.mypage

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.FragmentMpSoldBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage


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
        adapter.itemClickListener = object : SoldAdapter.OnItemClickListener{
            override fun OnItemClick(holder: RecyclerView.ViewHolder, data: Product, pos: Int) {
                binding.apply {
                    pName.text = data.pId
                    pauthor.text = data.pAuthor
                    pprice.text = data.pPrice.toString()
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.getReference()
                    val submitImg = storageRef.child(data.pPic)
                    submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {
                            Glide.with(binding.root)
                                .load(p0)
                                .into(binding.pimage)
                        }
                    })
                }

            }

        }

    }


}