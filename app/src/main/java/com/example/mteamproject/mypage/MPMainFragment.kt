package com.example.mteamproject.mypage

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.mteamproject.Art
import com.example.mteamproject.Artist
import com.example.mteamproject.R
import com.example.mteamproject.databinding.DialogCoinBinding
import com.example.mteamproject.databinding.FragmentMPMainBinding
import com.google.firebase.database.*
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MPMainFragment : Fragment() {
    lateinit var binding: FragmentMPMainBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var db : DatabaseReference

    val loadFragment = MPLoadFragment()
    val soldFragment = MPSoldFragment()
    val likeFragment = MPLikeFragment()
    val auctionFragment = MyAuctionFragment()
    val mpViewModel : MPViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMPMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initData(){
        myDBHelper = MyDBHelper(requireContext())
        mpViewModel.likesLiveData.value = myDBHelper.getAllRecord(0)

        mpViewModel.soldsLiveData.value = myDBHelper.getAllRecord(1)

        var loadList = mutableListOf<Product>()
        db = FirebaseDatabase.getInstance().getReference("Art/${mpViewModel.userLiveData.value}")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var children = snapshot.children
                if(snapshot.childrenCount>0) {
                    for (i in children.iterator()) {
                        //Log.i("data","${i.value as HashMap<String,String>}")
                        val value = i.value as HashMap<String,String>

                        val imageUrl =value["imageUrl"] as String
                        val title = value["title"] as String
                        val artist_name = value["userID"] as String
                        val price = value["sellPrice"] as Long
                        Log.d("EOEOEO","load??? ?????? ???????????? : $title")
                        loadList.add(Product(title, artist_name, imageUrl,price.toInt()))
                    }
                }
                mpViewModel.loadsLiveData.value = loadList
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }

        })
        mpViewModel.loadsLiveData.observe(viewLifecycleOwner, {
            Log.d("EOEOEO", "loadLiveData: ${mpViewModel.loadsLiveData.value!!.size}")
            binding.loadNum.text = it.size.toString()
        })

        Log.d("EOEOEO", "likeLiveData: ${mpViewModel.likesLiveData.value!!.size}")
        mpViewModel.likesLiveData.observe(viewLifecycleOwner, Observer {
            binding.likeNum.text = it.size.toString()
        })
    }

    private fun initView(){

        binding.userId.text = mpViewModel.userLiveData.value
        binding.apply {
            likeBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, likeFragment)
                    .addToBackStack(null)
                    .commit()
            }
            soldBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, soldFragment)
                    .addToBackStack(null)
                    .commit()
            }
            loadBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, loadFragment)
                    .addToBackStack(null)
                    .commit()
            }
            auctionBtn.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentView, auctionFragment)
                    .addToBackStack(null)
                    .commit()
            }

            fillCoin.setOnClickListener {
                var coin:Int
                val dlgBinding = DialogCoinBinding.inflate(layoutInflater)
                val dlgBuilder = AlertDialog.Builder(requireContext())
                dlgBuilder.setView(dlgBinding.root) //????????? ?????? dlg
                    .setPositiveButton("??????"){
                            _,_ ->
                        coin = dlgBinding.editText.text.toString().toInt()
                        val result = binding.coin.text.toString().toInt() + coin
                        binding.coin.text = result.toString()
                        //DB??? ??????
                        myDBHelper.insertCoin(result)
                    }
                    .setNegativeButton("??????"){
                            _,_ ->
                    }
                    .show()

            }
            // ?????? init
            coin.text = myDBHelper.getCoin().toString()
            likeNum.text = mpViewModel.likesLiveData.value!!.size.toString()
            soldNum.text = mpViewModel.soldsLiveData.value!!.size.toString()

        }
    }


}