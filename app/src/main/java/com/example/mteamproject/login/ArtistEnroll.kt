package com.example.mteamproject.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityArtistEnrollBinding
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.example.mteamproject.enroll.EnrollStorage
import com.example.mteamproject.main.MainActivity
import com.google.firebase.database.*

class ArtistEnroll : AppCompatActivity() {
    lateinit var binding: ActivityArtistEnrollBinding
    lateinit var adapter: ArtistListAdapter
    lateinit var rdb: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    val GET_GALLERY_IMAGE = 200
    var imgUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistEnrollBinding.inflate(layoutInflater)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        setContentView(binding.root)
        init()
    }

    fun init(){
        val intent = intent
        val uid = intent.getStringExtra("uId")
        val uname = intent.getStringExtra("uName")
        val uage = intent.getIntExtra("uAge", -1)

        rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")

        binding.apply {
            userID.text = uid.toString()
            userName.text = uname.toString()
            userAge.text = uage.toString()

            artistImg.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*")
                startActivityForResult(intent, GET_GALLERY_IMAGE)

                val intent2 = Intent(Intent.ACTION_PICK)
                intent2.setType(MediaStore.Images.Media.CONTENT_TYPE)
                intent2.setType("image/*")
            }

            artistEnrollBtn.setOnClickListener {
                if(imgUrl.length >=1) {
                    if (artistSpec.text.toString().length >= 1) {
                        rdb.child(uid.toString()).child("artist").setValue(true.toString().toBoolean())
                        rdb.child(uid.toString()).child("aspec").setValue(artistSpec.text.toString())
                        rdb.child(uid.toString()).child("zImgUrl").setValue(imgUrl.toString())
                        Toast.makeText(this@ArtistEnroll, "작가 등록 성공", Toast.LENGTH_SHORT).show()
                        editor.putBoolean("isArtist", true)
                        editor.commit()
                        val intent = Intent(this@ArtistEnroll, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@ArtistEnroll, "이력을 작성해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ArtistEnroll, "프로필 사진을 업로드해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!= null && data.data!=null) {
            val selectedImageUri = data.data
            binding.artistImg.setImageURI(selectedImageUri)

            val uploadImg = EnrollStorage(selectedImageUri!!)
            uploadImg.uploadImage()
            imgUrl = "/ArtImg/${selectedImageUri.lastPathSegment}"
        }
    }
}