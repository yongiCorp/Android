package com.example.brandol.board

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.brandol.databinding.FragmentBrandinfoWriteBoardBinding

class WriteBoardActivity : AppCompatActivity() {

    lateinit var binding: FragmentBrandinfoWriteBoardBinding
    val imageCount: Int = 0  //게시물의 사진 개수 초기화

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
        // Callback is invoked after the user selects media items or closes the photo picker.
        if (uris.isNotEmpty()) {
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            // 권한 부여
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            uris.forEach { uri ->
                applicationContext.contentResolver.takePersistableUriPermission(uri, flag)
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //게시글 사진 개수 세기
        val intent = Intent(this, WriteBoardActivity::class.java)
        intent.putExtra("imageCount", imageCount)
        startActivity(intent)


        //브랜드 자체 규칙 보여주기
        binding.writeRulecontentTv.text = "브랜드 규칙 와라랄라라라라라라라라라라라 와우와우와우 글씨늘리기 글씨늘리기 글씨늘리기 깔깔깔깔"

        //뒤로가기
        goBack()
        //갤러리 사진 가져오기
        viewImgPicker()
        //게시물 등록
        addPost()
        //게시글 사진 관련 코드
        addImage()
        //게시글 작성 중 취소 시 뜨는 Dialog 화면
        cancelPostCustomdialog()

        //게시글 제목 및 내용 저장하는 코드 작성

    }

    private fun goBack() {
        binding.writeBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun viewImgPicker() {
        binding.writeGalleryBtn.setOnClickListener {
            //커스텀 갤러리 화면
            // Launch the photo picker and let the user choose images and videos.
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }

    private fun addPost() {
        binding.writeAddBtn.setOnClickListener {
            //게시글 등록
        }
    }

    private fun addImage() {
        //if문으로 추후 처리, imageCount 개수 처리 코드 추가
    }

    private fun cancelPostCustomdialog() {
        //한 글자 혹은 사진이라도 입력 중에 뒤로가기를 누르면 뜨는 팝업

    }
}