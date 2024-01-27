package com.example.brandol

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class CustomAccountDialog(
    context: Context,
    private val message: String,
    private val onConfirm: () -> Unit,
    private val onCancel: () -> Unit
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_delete_account)


        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val messageTextView:TextView = findViewById(R.id.dialog_delete_account_tv)
        val confirmButton: Button = findViewById(R.id.dialog_account_yes_btn)
        val cancelButton: Button = findViewById(R.id.dialog_account_no_btn)

        messageTextView.text = message
        confirmButton.setOnClickListener {
            onConfirm.invoke()
            dismiss()
        }

        cancelButton.setOnClickListener {
            onCancel.invoke()
            dismiss()
        }
    }

}