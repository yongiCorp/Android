package com.example.brandol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandol.databinding.ActivityChattingBinding

class ChattingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChattingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val intent = Intent(this,OpponentAvatarActivity::class.java)
            startActivity(intent)
        }
        backbtn()
    }

    private fun backbtn() {
        binding.chattingBackBtn.setOnClickListener {
            finish()
        }
    }
}