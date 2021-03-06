package com.example.mteamproject.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityUserLoginBinding
import com.example.mteamproject.main.MainActivity
import com.example.mteamproject.main.SplashActivity
import com.google.firebase.database.*

class UserLogin : AppCompatActivity() {
    lateinit var binding: ActivityUserLoginBinding
    lateinit var rdb: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        init()
    }

    fun init(){
        if(sharedPreferences.getBoolean("AutoLogin", false)){
            val userID = sharedPreferences.getString("Id", "")
            val intent = Intent(this@UserLogin, SplashActivity::class.java)
            intent.putExtra("uId", userID)
            startActivity(intent)
            finish()
        }else {
            rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")
            binding.apply {
                loginBtn.setOnClickListener {
                    var idFlag = false
                    var arr = arrayListOf<Any>()
                    rdb.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (snapshot in p0.children) {
                                if (snapshot.key.equals(userID.text.toString())) {
                                    idFlag = true
                                    break
                                }
                            }
                            if (idFlag) {
                                rdb.child(userID.text.toString())
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                        }
                                        override fun onDataChange(p0: DataSnapshot) {
                                            var num = 0
                                            for (snapshot in p0.children) {
                                                arr.add(snapshot.value.toString())
                                                num++
                                                if(num == 9)
                                                    break
                                            }
                                            if (arr[8].toString() == userPW.text.toString()) {
                                                if (autoLoginCheckBox.isChecked) {
                                                    editor.putBoolean("AutoLogin", true)
                                                    editor.apply()
                                                    editor.putString("Id", userID.text.toString())
                                                    editor.putString("Password",userPW.text.toString())
                                                    editor.commit()
                                                } else {
                                                    editor.putBoolean("AutoLogin", false)
                                                    editor.putString("Id", userID.text.toString())
                                                    editor.putString("Password", userPW.text.toString())
                                                    editor.commit()
                                                }
                                                if(arr[0].toString().toBoolean()){
                                                    editor.putBoolean("isArtist", true)
                                                    editor.commit()
                                                }else{
                                                    editor.putBoolean("isArtist", false)
                                                    editor.commit()
                                                }

                                                Toast.makeText(this@UserLogin,
                                                    "????????? ??????",
                                                    Toast.LENGTH_SHORT).show()
                                                val intent =
                                                    Intent(this@UserLogin, SplashActivity::class.java)
                                                intent.putExtra("uId", userID.text.toString())
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                Toast.makeText(this@UserLogin,
                                                    "??????????????? ???????????????",
                                                    Toast.LENGTH_SHORT).show()
                                                userPW.text.clear()
                                            }
                                        }
                                    })
                            } else {
                                Toast.makeText(this@UserLogin, "??????????????? ???????????????", Toast.LENGTH_SHORT)
                                    .show()
                                userID.text.clear()
                                userPW.text.clear()
                            }
                        }
                    })

                }
                signUpBtn.setOnClickListener {
                    val intent = Intent(this@UserLogin, UserSignUp::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}