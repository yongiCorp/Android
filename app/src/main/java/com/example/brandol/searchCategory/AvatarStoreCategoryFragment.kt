package com.example.brandol.searchCategory

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.brandol.R
import com.example.brandol.SearchBarFragment
import com.example.brandol.adaptor.AvatarStoreAdapter
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.connection.RetrofitObject
import com.example.brandol.databinding.FragmentAvatarstoreCategoryBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvatarStoreCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAvatarstoreCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvatarstoreCategoryBinding.inflate(inflater, container, false)

        binding.btnSearchBarIb.setOnClickListener {
            val searchBarFragment = SearchBarFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm, searchBarFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.btnSearchIc.setOnClickListener {
            val searchBarFragment = SearchBarFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm, searchBarFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        getUserAvatarAndPointsData()
        return binding.root
    }

    private fun getCurrentToken(context: Context): String? {
        val sharedPref = context.getSharedPreferences("Brandol", Context.MODE_PRIVATE)
        return sharedPref.getString("accessToken", null)
    }

    private fun getUserAvatarAndPointsData() {
        val token = getCurrentToken(requireContext())
        val call = RetrofitObject.getRetrofitService.searchDetailUserAvatarAndPoints("Bearer $token")
        call.enqueue(object : Callback<RetrofitClient2.SearchDetailUserAvatarAndPoints> {
            override fun onResponse(
                call: Call<RetrofitClient2.SearchDetailUserAvatarAndPoints>,
                response: Response<RetrofitClient2.SearchDetailUserAvatarAndPoints>
            ) {
                Log.d("ikj", response.toString())
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("ikj", responseData.toString())
                    if (responseData != null && responseData.isSuccess) {
                        // 서버에서 받아온 아바타 이미지를 ImageView에 설정
                        Glide.with(requireContext()).load(responseData.result.memberAvatar)
                            .into(binding.characterIv)

                        // 서버에서 받아온 포인트를 TextView에 설정
                        binding.numPointTv.text = "${responseData.result.memberPoints}P"
                    }
                }
            }
            override fun onFailure(call: Call<RetrofitClient2.SearchDetailUserAvatarAndPoints>, t: Throwable) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("ikj", errorMessage)
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = binding.avatarStoreVp
        val tabLayout: TabLayout = binding.avatarStoreTabs

        val adapter = AvatarStoreAdapter(requireActivity())
        adapter.addFragment(AvatarStoreTabFragment.newInstance("전체"), "전체")
        adapter.addFragment(AvatarStoreTabFragment.newInstance("HAIR"), "헤어")
        adapter.addFragment(AvatarStoreTabFragment.newInstance("SKIN"), "피부")
        adapter.addFragment(AvatarStoreTabFragment.newInstance("TOP"), "상의")
        adapter.addFragment(AvatarStoreTabFragment.newInstance("BOTTOM"), "하의")
        adapter.addFragment(AvatarStoreTabFragment.newInstance("SHOES"), "신발")

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.titleList[position]
        }.attach()

        binding.btnBackAvatarstoreCategory.setOnClickListener {
            // 이전 화면으로 돌아가기
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
