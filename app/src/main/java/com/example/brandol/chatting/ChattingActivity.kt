package com.example.brandol.chatting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandol.ItemClickListener
import com.example.brandol.R
import com.example.brandol.adaptor.RV.ChattingRVAdapter
import com.example.brandol.collection.Chat
import com.example.brandol.databinding.ActivityChattingBinding
import com.example.brandol.opavatar.OpponentAvatarActivity

class ChattingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChattingBinding
    val email: String = "ww"
    private var chatList = ArrayList<Chat>()
    private val adapterForChat = ChattingRVAdapter(chatList, email)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //상단에 이름 바인딩 추후 백엔드 연결
        binding.chattingNameTv.text = intent.getStringExtra("messagekey")

        //리사이클러뷰 연결
        binding.chattingContentsRv.layoutManager = LinearLayoutManager(this)
        binding.chattingContentsRv.adapter = adapterForChat

        //유저 프로필 클릭시 상대 아바타 방으로 이동
        val intent = Intent(this, OpponentAvatarActivity::class.java)
        adapterForChat.itemClickListener = object : ItemClickListener {
            override fun onItemClick(position: Int) {
                val userInfo = chatList[position]
                intent.putExtra("from", "Chatting")
                intent.putExtra("chatkey", userInfo.name)
                startActivity(intent)
            }
        }
        //상대
        chatList.apply {
            add(Chat("w", "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ", R.drawable.demo_avatar3, "용기리우스"))
        }

        //보내기 버튼 눌렀을때 동작 추후에 백엔드 연결
        binding.chattingSendBtn.setOnClickListener {
            var chattext = binding.chattingEdittextEt.text.toString()
            chatList.add(Chat(email, chattext, R.drawable.demo_avatar3, "호진"))
            adapterForChat.notifyItemInserted(chatList.size - 1)
            binding.chattingEdittextEt.text.clear()
            /*// 메시지 보내고 받는 시간 받기
            val calendar: Calendar = Calendar.getInstance() // 캘린더 객체 인스턴스 calendar
            val dateFormat = SimpleDateFormat("yyyy-dd-MMM HH:mm:ss") // SimpleDataFormat 이라는 날짜와 시간을 출력하는 객체 생성, hh을 HH로 변경했더니 24시각제로 바뀜
            val datetime: String = dateFormat.format(calendar.time)*/
        }

        backbtn()
    }

    private fun backbtn() {
        binding.chattingBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LHJ", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LHJ", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LHJ", "onPause()")
    }
}