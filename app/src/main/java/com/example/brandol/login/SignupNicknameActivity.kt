// NicknameActivity.kt
package com.example.brandol.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brandol.MainActivity
import com.example.brandol.R
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.connection.RetrofitObject
import com.example.brandol.databinding.ActivitySignupNicknameBinding
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupNicknameActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 에딧텍스트의 텍스트 변경을 감지하는 TextWatcher 등록
        binding.nicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // 에딧텍스트에 텍스트가 입력되면 호출
                val isNicknameNotEmpty = charSequence?.isNotEmpty() == true

                // 텍스트가 입력되었을 때 보라색 버튼 표시, 그렇지 않으면 회색 버튼 표시
                if (isNicknameNotEmpty) {
                    binding.signUpOkB.visibility = android.view.View.VISIBLE
                    binding.signUpNoB.visibility = android.view.View.GONE
                } else {
                    binding.signUpOkB.visibility = android.view.View.GONE
                    binding.signUpNoB.visibility = android.view.View.VISIBLE
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        binding.nicknameBackBtn.setOnClickListener {
            finish()
        }

        // 각 체크박스들의 체크 상태 변경 시
        val checkBoxChangeListener = CompoundButton.OnCheckedChangeListener { _, _ ->
            // 모든 체크박스들의 상태를 확인하여 다음 버튼을 표시하거나 숨김
            updateNextButtonVisibility()
        }

        // 각 체크박스들에 리스너 등록
        binding.maleCb.setOnCheckedChangeListener(checkBoxChangeListener)
        binding.femaleCb.setOnCheckedChangeListener(checkBoxChangeListener)

        // 체크 상태 변경 리스너
        binding.maleCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.femaleCb.isChecked = false
            }
            updateNextButtonVisibility()
        }
        binding.femaleCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.maleCb.isChecked = false
            }
            updateNextButtonVisibility()
        }



        binding.signUpOkB.setOnClickListener {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e("LHJ", "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Log.d("LHJ", "사용자 정보 요청 성공")
                    val email = user.kakaoAccount?.email
                    //val email = "j@gmail.com"
                    Log.d("LHJ",email.toString())
                    val termsIdList : List<Long> = listOf<Long>(1, 2, 3, 4, 5, 6)
                    Log.d("LHJ",termsIdList.toString())
                    val nickname : String= binding.nicknameEt.text.toString()
                    Log.d("LHJ",nickname)
                    val gender : String = "MALE"
                    val age = 23
                    signupServer(email, termsIdList, nickname, gender, age)
                    //val intent = Intent(this@SignupNicknameActivity, LoginStartActivity::class.java)
//                    val intent = Intent(this@SignupNicknameActivity, MainActivity::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                    finish()
                    // 이때, 새로운 계정 정보를 Intent에 추가
                    val intent = Intent(this@SignupNicknameActivity, MainActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("gender", gender)
                    intent.putExtra("age", age)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }

        }

        // 에딧텍스트 클릭 시 힌트 텍스트 삭제
        binding.nicknameEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.nicknameEt.text = null
                binding.nicknameEt.setTextColor(resources.getColor(R.color.black))
            } else {
                // 에딧텍스트가 포커스를 잃으면서 텍스트가 비어있을 경우 힌트 텍스트를 다시 설정
                if (binding.nicknameEt.text.isEmpty()) {
                    binding.nicknameEt.setTextColor(resources.getColor(R.color.gray))
                }
            }
        }

        // 초기 상태에서는 보라색 버튼은 숨겨져 있고, 회색 버튼이 표시되어 있어야 함
        binding.signUpOkB.visibility = android.view.View.GONE
        binding.signUpNoB.visibility = android.view.View.VISIBLE
    }

    private fun signupServer(
        email: String?,
        termsIdList: List<Long>,
        nickname: String,
        gender: String,
        age: Int
    ) {
        val call = RetrofitObject.getRetrofitService.signup(
            RetrofitClient2.RequestSignup(
                email!!, termsIdList, nickname, gender, age
            )
        )
        call.enqueue(object : Callback<RetrofitClient2.ResponseSignup> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseSignup>,
                response: Response<RetrofitClient2.ResponseSignup>
            ) {
                Log.d("LHJ", response.toString())
                if (response.isSuccessful) {
                    val response = response.body()
                    Log.d("LHJ", response.toString())
                    if (response != null) {
                        if (response.isSuccess) {
                            Log.d("LHJ", response.toString())
                            val memberId = response.result.memberId
                            val signUp = response.result.signUp
                            saveMemberInfo(this@SignupNicknameActivity,memberId,signUp)
                        } else {
                            Toast.makeText(
                                this@SignupNicknameActivity,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Log.d("LHJ", response.toString())
                }
            }

            override fun onFailure(
                call:
                Call<RetrofitClient2.ResponseSignup>, t: Throwable
            ) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("LHJ", errorMessage)
            }
        })
    }
    private fun saveMemberInfo(context: Context, memberId: Long?,signUp: Boolean? )
    {
        val sharedPref = context.getSharedPreferences("Brandol", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            memberId?.let { putLong("memberId", it) }
            signUp?.let { putBoolean("signUp", it) }
            apply()
        }
    }


    private fun updateNextButtonVisibility() {
        // 모든 하위 체크박스들의 상태 확인
        val isCheckboxMaleChecked = binding.maleCb.isChecked
        val isCheckboxFemaleChecked = binding.femaleCb.isChecked


        // 모든 체크박스가 선택되었을 때
        if (isCheckboxFemaleChecked || isCheckboxMaleChecked ) {
            // 다음 버튼 표시, 회색 버튼 숨김
            binding.signUpOkB.visibility = android.view.View.VISIBLE
            binding.signUpNoB.visibility = android.view.View.GONE
        } else {
            // 그 외의 경우 다음 버튼 숨김, 회색 버튼 표시
            binding.signUpOkB.visibility = android.view.View.GONE
            binding.signUpNoB.visibility = android.view.View.VISIBLE
        }
    }


}
