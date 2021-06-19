package com.example.mteamproject.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityAsdBinding
import com.example.mteamproject.databinding.ActivityUserLoginBinding
import com.example.mteamproject.mypage.Product
import com.google.firebase.database.FirebaseDatabase

class asdActivity : AppCompatActivity() {
    lateinit var binding: ActivityAsdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val intent = intent
        val uid = intent.getStringExtra("uId")
        binding.apply {
            button.text = uid
            button.setOnClickListener {
                var rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")
                //val item = User("123", "1234", "유후", 30, false, 0, 0, 0, "")

                val item = User("123","123", "홍길동", 26, true,  24,10, 13, "홍길동 이력")
                val item1 = User("a123", "a123", "유후", 25, true, 10,13, 40, "유후 이력")
                val item2 = User("b123", "a123", "안드레이", 24, true,  30,12, 77, "안드레이 이력")
                val item3 = User("c123", "a123", "김유신", 28, true, 40,23, 44, "김유신 이력")
                val item4 = User("d123", "a123", "name", 29, true, 5,1, 5, "name 이력")
                val item5 = Product1("3", "안작1", "안드레이",0, 5000)
                val item6 = Product1("113", "안작2", "안드레이",0, 3000)
                val item7 = Product1("23", "안작3", "안드레이",0, 1000)
                val item8 = Product1("300", "안작4", "안드레이",0, 2000)

                val item9 = Product1("4", "홍길동작품1", "홍길동",0, 5000)
                val item10 = Product1("117", "홍길동작품2", "홍길동",0, 3000)
                val item11 = Product1("24", "홍길동작품3", "홍길동",0, 1000)
                val item12 = Product1("301", "홍길동작품4", "홍길동",0, 2000)

                val item13 = Product1("5", "유후작품1", "유후",0, 5000)
                val item14 = Product1("114", "유후작품2", "유후",0, 3000)
                val item15 = Product1("25", "유후작품3", "유후",0, 1000)
                val item16 = Product1("302", "유후작품4", "유후",0, 2000)

                val item17 = Product1("6", "김유신작품1", "김유신",0, 5000)
                val item18 = Product1("115", "김유신작품2", "김유신",0, 3000)
                val item19 = Product1("26", "김유신작품3", "김유신",0, 1000)
                val item20 = Product1("303", "김유신작품4", "김유신",0, 2000)

                val item21 = Product1("7", "name작품1", "name",0, 5000)
                val item22 = Product1("116", "name작품2", "name",0, 3000)
                val item23 = Product1("27", "name작품3", "name",0, 1000)
                val item24 = Product1("304", "name작품4", "name",0, 2000)
                /*rdb.child("123").setValue(item)
                rdb.child("a123").setValue(item1)
                rdb.child("b123").setValue(item2)
                rdb.child("c123").setValue(item3)
                rdb.child("d123").setValue(item4)
                rdb.child("c123").child("artList").child(1.toString()).setValue(3)
                rdb.child("c123").child("artList").child(2.toString()).setValue(113)
                rdb.child("c123").child("artList").child(3.toString()).setValue(23)
                rdb.child("c123").child("artList").child(4.toString()).setValue(300)
                rdb.child("123").child("artList").child(1.toString()).setValue(2)
                rdb.child("123").child("artList").child(2.toString()).setValue(114)
                rdb.child("123").child("artList").child(3.toString()).setValue(24)
                rdb.child("123").child("artList").child(4.toString()).setValue(301)
                rdb.child("a123").child("artList").child(1.toString()).setValue(4)
                rdb.child("a123").child("artList").child(2.toString()).setValue(115)
                rdb.child("a123").child("artList").child(3.toString()).setValue(25)
                rdb.child("a123").child("artList").child(4.toString()).setValue(302)
                rdb.child("b123").child("artList").child(1.toString()).setValue(5)
                rdb.child("b123").child("artList").child(2.toString()).setValue(116)
                rdb.child("b123").child("artList").child(3.toString()).setValue(26)
                rdb.child("b123").child("artList").child(4.toString()).setValue(303)
                rdb.child("d123").child("artList").child(1.toString()).setValue(6)
                rdb.child("d123").child("artList").child(2.toString()).setValue(117)
                rdb.child("d123").child("artList").child(3.toString()).setValue(27)
                rdb.child("d123").child("artList").child(4.toString()).setValue(304)

                rdb.child("123").setValue(item)
                rdb.child("a123").setValue(item1)
                rdb.child("b123").setValue(item2)
                rdb.child("c123").setValue(item3)
                rdb.child("d123").setValue(item4)
                rdb.child("b123").child("aspec").setValue("평양시 보통강구역 원 보통강동지역에서 출생하였다.\n" +
                        "1984년에 평양미술대학 조선화학부를 졸업하였다.\n" +
                        "만수대창작사에서 15년간 창작생활을 하고 있다.\n" +
                        "\n" +
                        "조선화 ‘세상에 부럼없어라’(1990, 228×180cm, 국가미술전람회에서 금메달),\n" +
                        "‘청춘거리 건설장을 찾으신 친애하는 지도자 김정일동지’(1989),\n" +
                        "‘현지지도의 길에서 주체농법을 가르쳐주시는 위대한 수령님’(1992),\n" +
                        "‘산촌의 밤길을 걸으시며’(1992), ‘유다섬회의를 지도하시는 위대한 수령 김일성동지’(1993),\n" +
                        "‘전사의 대답을 들으시며’(1994), ‘서재에서’(1994), ‘풍년 든 강냉이포전을 찾으시어’(1994),\n" +
                        "‘우리 인민이 사는 곳이라면’(1995), ‘풍년 든 포전마다’(1996),\n" +
                        "‘판문점 민경초소를 찾아주신 위대한 김정일 장군’(1997) 등 적지 않은\n" +
                        "주요주제의 작품을 내놓았다.\n" +
                        "\n" +
                        "주요대상의 풍경으로 ‘밀림의 가을’(1985), ‘영암호의 가을’(1988),\n" +
                        "‘백두밀영의 봄’(1992) 등의 창작에 참가하였다.\n" +
                        "일반주제 및 풍경화들로는 ‘변치 말자 다진 맹세’(1987), ‘2월의 련광정’(1987),\n" +
                        "‘지원자들’(1988), ‘련광정’(1990) 등이 대표작이다.\n" +
                        "1997년에 공훈예술가의 칭호를 수여받았다.")*/
                rdb.child("b123").child("zArtList").child(1.toString()).setValue(item5)
                rdb.child("b123").child("zArtList").child(2.toString()).setValue(item6)
                rdb.child("b123").child("zArtList").child(3.toString()).setValue(item7)
                rdb.child("b123").child("zArtList").child(4.toString()).setValue(item8)

                rdb.child("123").child("zArtList").child(1.toString()).setValue(item9)
                rdb.child("123").child("zArtList").child(2.toString()).setValue(item10)
                rdb.child("123").child("zArtList").child(3.toString()).setValue(item11)
                rdb.child("123").child("zArtList").child(4.toString()).setValue(item12)

                rdb.child("a123").child("zArtList").child(1.toString()).setValue(item13)
                rdb.child("a123").child("zArtList").child(2.toString()).setValue(item14)
                rdb.child("a123").child("zArtList").child(3.toString()).setValue(item15)
                rdb.child("a123").child("zArtList").child(4.toString()).setValue(item16)

                rdb.child("c123").child("zArtList").child(1.toString()).setValue(item17)
                rdb.child("c123").child("zArtList").child(2.toString()).setValue(item18)
                rdb.child("c123").child("zArtList").child(3.toString()).setValue(item19)
                rdb.child("c123").child("zArtList").child(4.toString()).setValue(item20)

                rdb.child("d123").child("zArtList").child(1.toString()).setValue(item21)
                rdb.child("d123").child("zArtList").child(2.toString()).setValue(item22)
                rdb.child("d123").child("zArtList").child(3.toString()).setValue(item23)
                rdb.child("d123").child("zArtList").child(4.toString()).setValue(item24)

            }
        }
    }
}