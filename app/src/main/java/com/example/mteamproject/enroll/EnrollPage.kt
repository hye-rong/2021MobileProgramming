package com.example.mteamproject.enroll

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mteamproject.R
import com.example.mteamproject.databinding.AcitivityEnrollartBinding

class EnrollPage: AppCompatActivity() {
    val GET_GALLERY_IMAGE = 200

    lateinit var binding: AcitivityEnrollartBinding

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
        }
    }

    private fun sellOption() {
        binding.pickSell.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.fixed_price -> {
                    binding.sellPriceInput.isClickable = true
                    binding.sellPriceInput.isFocusable = true
                }
                R.id.auction -> {
                    //경매는 판매가격 입력할 필요 없음
                    binding.sellPriceInput.isClickable = false
                    binding.sellPriceInput.isFocusable = false
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }
    }

    private fun pickEndDate() {
        binding.pickDay.setOnClickListener {

        }
    }

    private fun upload() {
        binding.sendToDB.setOnClickListener {

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!= null && data.data!=null) {
            val selectedImageUri = data.data
            binding.enrollImg.setImageURI(selectedImageUri)
        }
    }
}