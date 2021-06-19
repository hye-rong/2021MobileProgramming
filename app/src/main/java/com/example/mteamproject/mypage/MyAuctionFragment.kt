package com.example.mteamproject.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.databinding.FragmentMyAuctionBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class MyAuctionFragment : Fragment() {
    lateinit var binding: FragmentMyAuctionBinding
    lateinit var auctionList:MutableList<Product>
    lateinit var adapter: AuctionAdpater
    lateinit var myDBHelper: MyDBHelper
    lateinit var db : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyAuctionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    private fun initData(){
        db = FirebaseDatabase.getInstance().getReference("ArtRCV")
        myDBHelper = MyDBHelper(requireContext())
        auctionList = myDBHelper.getAuction()
        adapter = AuctionAdpater(auctionList)

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.adapter = adapter
        }
        adapter.itemClickListener = object : AuctionAdpater.OnItemClickListener{
            override fun OnItemClick(holder: RecyclerView.ViewHolder, data: Product, pos: Int) {
                //작품 결과 다이얼로그 띄우기
                // firebase의 endDate와 price 확인
                db.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var children = snapshot.children
                        if (snapshot.childrenCount > 0) {
                            for (i in children.iterator()) {
                                if (i.key == data.pAuthor) {
                                    val value = i.value as HashMap<String, String>
                                    val price = value["sellPrice"] as Long
                                    var edate = value["endDate"].toString()
                                    Log.d("eoeoeo", "$edate")
                                    edate = edate.replace("[", "")
                                    edate = edate.replace("]","")
                                    edate = edate.replace(" ", "")
                                    val el = edate.split(",")
                                    val endDate = getDate(el[0].toInt(), el[1].toInt(),el[2].toInt(),el[3].toInt(),el[4].toInt(),0)

                                    val now: Long = System.currentTimeMillis()

                                    val date = Date(now)
                                    Log.d("eoeoeo", "현재시간: $date")
                                    Log.d("eoeoeo", "종료시간: $endDate")

                                    if(endDate.after(date)){
                                        if(data.pPrice == price.toInt()){
                                            Toast.makeText(requireContext(),"경매 성공", Toast.LENGTH_LONG).show()
                                            adapter.items.removeAt(pos)
                                            adapter.notifyItemRemoved(pos)
                                            val title = value["title"] as String
                                            val artist_name = value["userID"] as String
                                            myDBHelper.insertPurchaseProduct(Product(title, artist_name, data.pPic, price.toInt()))
                                            myDBHelper.deleteAuction(title)
                                        }
                                        else{
                                            Toast.makeText(requireContext(),"경매 실패", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    else{
                                        Toast.makeText(requireContext(),"경매 진행중 현재 금액:${price}", Toast.LENGTH_LONG).show()
                                    }

                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //TODO("Not yet implemented")
                    }

                })

            }

        }
    }
    fun getDate(year:Int, month:Int, date:Int, hour:Int, minute:Int, second:Int):Date {

        Log.d("eoeoeo", "$year / $month / $date / $hour / $minute")
        val cal = Calendar.getInstance()

        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month-1)
        cal.set(Calendar.DATE, date)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, second)

        return cal.getTime();

    }


}