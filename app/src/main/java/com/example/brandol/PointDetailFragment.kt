//package com.example.brandol
import PointDetailVPAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brandol.databinding.FragmentPointDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PointDetailFragment : Fragment() {

    private lateinit var binding: FragmentPointDetailBinding
    private val tabTitles = arrayOf("전체", "획득", "사용")
    //private val selectedColor = Color.parseColor("#800080") // 보라색

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewPager2 어댑터 설정
        val adapter = PointDetailVPAdapter(this)
        binding.pointUsePageVp.adapter = adapter

        // ViewPager2와 TabLayout 연결
        TabLayoutMediator(binding.pointUsePageTb, binding.pointUsePageVp) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }
}
