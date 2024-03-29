package com.example.brandol

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.connection.RetrofitObject
import com.example.brandol.databinding.FragmentSearchBinding
import com.example.brandol.searchCategory.CatagoryFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)  // 데이터 바인딩 초기화

        // ViewPager2 초기화
        binding.catagoryContentVp.adapter = CategoryPagerAdapter(requireActivity())

        binding.btnSearchBarIb.setOnClickListener {
            // 서치바 클릭시 서치바 프레그먼트로 화면 전환
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frm,SearchBarFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        // 데이터 가져오기 및 화면 갱신
//        lifecycleScope.launch {
//            searchMain()
//        }


        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false)
    }

//    private fun getCurrentToken(context: Context): String?{
//        val sharedPref = context.getSharedPreferences("Brandol", AppCompatActivity.MODE_PRIVATE)
//        return sharedPref.getString("accessToken", null)
//    }
//
//    private fun searchMain() {
//        val token = getCurrentToken(requireContext())
//
//        val call = RetrofitObject.getRetrofitService.getSearchMain("Bearer $token")
//        Log.d("search_brand", "good_1")
//        call.enqueue(object : Callback<RetrofitClient2.ResponseSearchMain> {
//            override fun onResponse(
//                call: Call<RetrofitClient2.ResponseSearchMain>,
//                response: Response<RetrofitClient2.ResponseSearchMain?>
//            ) {
//                Log.d("search_brand", "good_2")
//                Log.d("search_brand", response.toString())
//                if (response.isSuccessful) {
//                    val response = response.body()
//                    Log.d("search_brand", response.toString())
//                    if (response != null) {
//                        if (response.isSuccess) {
//                            Log.d("search_brand", response.result.toString())
//
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(
//                call: Call<RetrofitClient2.ResponseSearchMain>,
//                t: Throwable
//            ) {
//                val errorMessage = "Call Failed: ${t.message}"
//                Log.e("sseohyeonn", errorMessage)
//            }
//        })
//    }




    private inner class CategoryPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            // Return the number of fragments/pages
            return 1
        }

        override fun createFragment(position: Int): Fragment {
            // Return the fragment associated with the specified position
            return CatagoryFragment() // Assuming CategoryFragment is the fragment_catagory.xml fragment
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}