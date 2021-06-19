package com.example.mteamproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityUserLoginBinding
import com.example.mteamproject.databinding.ActivityUserSignUpBinding
import com.google.firebase.database.*

class UserSignUp : AppCompatActivity() {
    lateinit var binding: ActivityUserSignUpBinding
    lateinit var rdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")

        var idFlag = false
        var signFlag = false

        with(binding) {
            idCheckBtn.setOnClickListener{
                idFlag = false

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
                        if(idFlag){
                            Toast.makeText(this@UserSignUp, "해당 아이디는 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                            userID.text.clear()
                        }else{
                            Toast.makeText(this@UserSignUp, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show()
                            signFlag = true
                        }
                    }
                })

            }
            signUpBtn.setOnClickListener {
                if(signFlag){
                    if(userPW.text.toString().length>=4) {
                        if (userPW.text.toString() == checkPW.text.toString()) {
                            if (userName.text.toString().length >= 1) {
                                if (userAge.text.toString().length >= 1) {
                                    val item = User(userID.text.toString(), userPW.text.toString(), userName.text.toString(), userAge.text.toString().toInt(), false, 0, 0, 0, "")
                                    rdb.child(userID.text.toString()).setValue(item)
                                    Toast.makeText(this@UserSignUp, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@UserSignUp, UserLogin::class.java)
                                    startActivity(intent)
                                }else {
                                    Toast.makeText(this@UserSignUp,"사용자의 나이을 입력 바랍니다.", Toast.LENGTH_SHORT).show()
                                }
                            }else {
                                Toast.makeText(this@UserSignUp,"사용자의 이름을 입력 바랍니다.",Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@UserSignUp,"비밀번호와 재확인 비밀번호가 서로 다릅니다.",Toast.LENGTH_SHORT).show()
                            checkPW.text.clear()
                        }
                    }else{
                        Toast.makeText(this@UserSignUp, "4자리 이상의 비밀번호를 입력 바랍니다.", Toast.LENGTH_SHORT).show()
                        userPW.text.clear()
                    }
                }else{
                    Toast.makeText(this@UserSignUp, "아이디 중복확인 바랍니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}