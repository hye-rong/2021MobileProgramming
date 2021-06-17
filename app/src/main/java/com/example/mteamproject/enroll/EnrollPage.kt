package com.example.mteamproject.enroll

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mteamproject.R
import com.example.mteamproject.databinding.AcitivityEnrollartBinding
import java.io.File

class EnrollPage: AppCompatActivity() {
    val GET_GALLERY_IMAGE = 200
    var imgUrl: String = ""
    var ifauction : Boolean = true //T면 경매, F면 정가제 판
    var userGenre : String = "" //장르
    var enrollTime : String = ""
    var sellPrice : Int = 0

    lateinit var binding: AcitivityEnrollartBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityEnrollartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getImg()
        sellOption()
        initSpinner()
        pickEndDate()
        upload()
    }

    private fun getImg() {
        binding.enrollImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, GET_GALLERY_IMAGE)

            val intent2 = Intent(Intent.ACTION_PICK)
            intent2.setType(MediaStore.Images.Media.CONTENT_TYPE)
            intent2.setType("image/*")
        }
    }

    private fun sellOption() {
        binding.pickSell.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.fixed_price -> {
                    binding.sellPriceInput.visibility = View.VISIBLE
                    ifauction=false
                }
                R.id.auction -> {
                    //경매는 판매가격 입력할 필요 없음
                    binding.sellPriceInput.visibility = View.INVISIBLE
                    ifauction = true
                }
            }
        }
    }

    private fun initSpinner() {
        val items = resources.getStringArray(R.array.genre_array)
        binding.enrollGenre.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)

        binding.enrollGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var genInput = items[position]
                userGenre = genInput.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }
    }

    private fun pickEndDate() {
        binding.pickDay.setOnClickListener {
            //달력
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month =  calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.enrollDay.text = "${year}년${month + 1}월${dayOfMonth}일"
                    enrollTime = "${year}년${month + 1}월${dayOfMonth}일"
                }

            }

            var builder = DatePickerDialog(this, dpd, year, month, day)
            builder.show()
        }
        binding.pickTime.setOnClickListener {
            //시계
            var time = Calendar.getInstance()
            var hour = time.get(Calendar.HOUR)
            var minute = time.get(Calendar.MINUTE)

            var tpd = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    binding.enrollTime.text = "${hourOfDay}시${minute}분"
                    enrollTime += "${hourOfDay}시${minute}분"
                }
            }

            var builder2 = TimePickerDialog(this, tpd, hour, minute, false)
            builder2.show()
        }
    }

    private fun upload() {
        //todo
        // 유저 id를 어떻게 받아올 것인가?

        binding.sendToDB.setOnClickListener {

            sellPrice = binding.sellPriceInput.text.toString().toInt()

            //var userID = sharedPreferences.getString("inputId", null)

            val enrollInput = EnrollData( "userID",
                imgUrl,
                userGenre,
                sellPrice,
                ifauction,
                enrollTime
            )
            var myEnrollDB = EnrollDB(enrollInput)
            myEnrollDB.addEnrollDB()

            Toast.makeText(this, "상품 등록 완료", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!= null && data.data!=null) {
            val selectedImageUri = data.data
            binding.enrollImg.setImageURI(selectedImageUri)

            val uploadImg = EnrollStorage(selectedImageUri!!)
            uploadImg.uploadImage()
            imgUrl = "/ArtImg/${selectedImageUri.lastPathSegment}"
        }
    }
}