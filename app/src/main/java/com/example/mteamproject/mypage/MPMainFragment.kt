package com.example.mteamproject.mypage

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import com.example.mteamproject.R
import com.example.mteamproject.databinding.DialogCoinBinding
import com.example.mteamproject.databinding.FragmentMPMainBinding
import java.util.*


class MPMainFragment : Fragment() {
    lateinit var binding: FragmentMPMainBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var sharedPreferences: SharedPreferences

    val loadFragment = MPLoadFragment()
    val soldFragment = MPSoldFragment()
    val likeFragment = MPLikeFragment()

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
        Log.d("EOEOEO", "${myDBHelper.getAllRecord(0).size}")
        mpViewModel.likesLiveData.value = myDBHelper.getAllRecord(0)

        mpViewModel.soldsLiveData.value = myDBHelper.getAllRecord(1)
        //내 작품 가져오는 작업 해줘야함 - firebase



        Log.d("EOEOEO", "LiveData: ${mpViewModel.likesLiveData.value!!.size}")
        mpViewModel.likesLiveData.observe(viewLifecycleOwner, Observer {
            binding.likeNum.text = it.size.toString()
        })
    }

    private fun initView(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val userId = sharedPreferences.getString("Id","null")
        Log.d("eoeoeo", userId!!)
        binding.userId.text = userId
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
            registerArtistBtn.setOnClickListener {
                //작가 등록 Activity로 이동
            }
            uploadBtn.setOnClickListener {
                //작품 등록 Activity로 이동
            }
            fillCoin.setOnClickListener {
                var coin:Int
                val dlgBinding = DialogCoinBinding.inflate(layoutInflater)
                val dlgBuilder = AlertDialog.Builder(requireContext())
                dlgBuilder.setView(dlgBinding.root) //우리가 만든 dlg
                    .setPositiveButton("추가"){
                            _,_ ->
                        coin = dlgBinding.editText.text.toString().toInt()
                        val result = binding.coin.text.toString().toInt() + coin
                        binding.coin.text = result.toString()
                        //DB에 추가
                        myDBHelper.insertCoin(result)
                    }
                    .setNegativeButton("취소"){
                            _,_ ->
                    }
                    .show()

            }
            // 숫자 init
            coin.text = myDBHelper.getCoin().toString()
            likeNum.text = mpViewModel.likesLiveData.value!!.size.toString()
            soldNum.text = mpViewModel.soldsLiveData.value!!.size.toString()
            //loadNum.setText()
        }
    }


}