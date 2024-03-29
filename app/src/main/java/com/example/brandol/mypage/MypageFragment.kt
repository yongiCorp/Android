package com.example.brandol.mypage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.brandol.R
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.connection.RetrofitObject
import com.kakao.sdk.user.UserApiClient
import com.example.brandol.databinding.FragmentMypageBinding
import com.example.brandol.dialog.CustomAccountDialog
import com.example.brandol.dialog.CustomLogoutDialog
import com.example.brandol.login.LoginStartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypageBinding.inflate(inflater, container, false)

        val token = getCurrentToken(requireContext())
        val call = RetrofitObject.getRetrofitService.getMypageData("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.ResponseMyInfo> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseMyInfo>,
                response: Response<RetrofitClient2.ResponseMyInfo>
            ) {
                Log.d("LHJ", response.toString())
                if (response.isSuccessful) {
                    val response = response.body()
                    Log.d("LHJ", response.toString())
                    if (response != null) {
                        if (response.isSuccess) {
                            Glide.with(binding.mypageProfileIv.context).load(response.result.avatar).into(binding.mypageProfileIv)
                            binding.mypageNameTv.text = response.result.nickname
                            binding.mypagePointQuantityTv.text = response.result.point.toString()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.ResponseMyInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        //회원정보
        binding.mypageUserinfoTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MypageUserinfoFragment())
                .addToBackStack(null)
                .commit()
        }
        //푸시알림
        binding.mypagePushAlarmTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MypagePushalarmFragment())
                .addToBackStack(null)
                .commit()
        }
        //차단목록
        binding.mypageBlacklistTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MypageBlacklistFragment())
                .addToBackStack(null)
                .commit()
        }
        //이용약관
        binding.mypageTermuseTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MypageTermuseFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.mypageLogoutTv.setOnClickListener {
            val context = context
            val dialog = CustomLogoutDialog(
                context!!, "로그아웃 하시겠습니까?",
                {
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Log.d("카카오", "카카오 로그아웃 실패")
                        } else {
                            Log.d("카카오", "카카오 로그아웃 성공!")

                            // 로그아웃 성공 시 LoginActivity로 이동
                            val intent = Intent(context, LoginStartActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            activity?.finish()
                        }
                    }
                    activity?.finish()
                },
                {
                    // 취소 버튼 클릭 시 동작

                }
            )
            dialog.show()
        }

        binding.mypageAccountDeleteTv.setOnClickListener {
            val context = context
            val dialog = CustomAccountDialog(
                context!!, "정말로 계정을 탈퇴하시겠습니까?\n" +
                        "탈퇴를 계속하기 원하신다면\n" +
                        "닉네임 입력 후 확인을 눌러주세요.",binding.mypageNameTv.text.toString(),
                {
                    val token = getCurrentToken(requireContext())
                    val call = RetrofitObject.getRetrofitService.deleteAccount("Bearer $token")
                    call.enqueue(object :Callback<RetrofitClient2.ResponseStatus>{
                        override fun onResponse(
                            call: Call<RetrofitClient2.ResponseStatus>,
                            response: Response<RetrofitClient2.ResponseStatus>
                        ) {
                            Log.d("LHJ", response.toString())
                            if (response.isSuccessful) {
                                val response = response.body()
                                Log.d("LHJ", response.toString())
                                if (response != null) {
                                    if (response.isSuccess) {

                                    }
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<RetrofitClient2.ResponseStatus>,
                            t: Throwable
                        ) {
                            TODO("Not yet implemented")
                        }

                    })
                    activity?.finish()
                },
                {
                    // 취소 버튼 클릭 시 동작

                }
            )
            dialog.show()
        }

        return binding.root
    }

    private fun getCurrentToken(context: Context): String?{
        val sharedPref = context.getSharedPreferences("Brandol", AppCompatActivity.MODE_PRIVATE)
        return sharedPref.getString("accessToken", null)
    }
    private fun setimage() {

    }
}