package com.example.brandol.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.brandol.R
class CustomDeleteDialog(
    context: Context,
    private val message: String,
    private val confirmButtonText: String,  // 추가: 확인 버튼 텍스트
    private val onConfirm: () -> Unit,
    private val onCancel: () -> Unit
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_delete_brand)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val yesButton: Button = findViewById(R.id.dialog_yes_btn)
        val noButton: Button = findViewById(R.id.dialog_no_btn)
        var textView: TextView = findViewById(R.id.dialog_delete_tv)

        textView.text = message
        yesButton.text = confirmButtonText  // 추가: 확인 버튼 텍스트 설정

        yesButton.setOnClickListener {
            onConfirm.invoke()
            dismiss()
        }

        noButton.setOnClickListener {
            onCancel.invoke()
            dismiss()
        }
    }
}
