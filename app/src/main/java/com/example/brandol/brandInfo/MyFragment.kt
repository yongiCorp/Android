package com.example.brandol.brandInfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.brandol.R
import com.example.brandol.board.BoardActivity
import com.example.brandol.connection.RetrofitAPI
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.databinding.FragmentCommuBinding
import com.example.brandol.databinding.FragmentMyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private lateinit var retrofitAPI: RetrofitAPI // Declare RetrofitAPI instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)

        // MyFragment가 생성될 때 브랜드 ID와 회원 ID를 가져옴
        val brandId: Long? = arguments?.getLong("brandId")
        var memberId: Long? = 123  //추후 수정

        // loadMyCategory() 함수에 브랜드 ID와 회원 ID를 전달하여 호출
        loadMyCategory(brandId, memberId)

        binding.myPlus1Btn.setOnClickListener {
            navigateToBoardActivity("MY", "내가 작성한 글", 401)
        }

        binding.myPlus2Btn.setOnClickListener {
            navigateToBoardActivity("MY", "내가 작성한 댓글", 402)
        }

        return binding.root
    }

    private fun navigateToBoardActivity(category: String, boardText: String, requestCode: Int) {
        val intent = Intent(requireContext(), BoardActivity::class.java)
        intent.putExtra("boardNowcateText", category)
        intent.putExtra("boardNowboardText", boardText)
        startActivityForResult(intent, requestCode)
    }

    private fun loadMyCategory(brandId: Long?, memberId: Long?) {
        // Retrofit을 사용하여 API 호출
        val callArticle: Call<RetrofitClient2.MyWrittenArticlesResponse> = retrofitAPI.myArticles(brandId, memberId)
        callArticle.enqueue(object : Callback<RetrofitClient2.MyWrittenArticlesResponse> {
            override fun onResponse(call: Call<RetrofitClient2.MyWrittenArticlesResponse>, response: Response<RetrofitClient2.MyWrittenArticlesResponse>) {
                if (response.isSuccessful) {
                    val myWrittenArticlesResponse = response.body()
                    myWrittenArticlesResponse?.let {
                        updateArticle(it)
                    }
                } else {
                    // API 호출은 성공했지만 응답이 실패한 경우에 대한 처리
                    Log.e("Myfragment", "Failed to load my article: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.MyWrittenArticlesResponse>, t: Throwable) {
                // 네트워크 호출 실패에 대한 처리
                Log.e("Myfragment", "Failed to load my article", t)
            }
        })

        val callComment: Call<RetrofitClient2.MyWrittenCommentsResponse> = retrofitAPI.myComments(brandId, memberId)
        callComment.enqueue(object : Callback<RetrofitClient2.MyWrittenCommentsResponse> {
            override fun onResponse(call: Call<RetrofitClient2.MyWrittenCommentsResponse>, response: Response<RetrofitClient2.MyWrittenCommentsResponse>) {
                if (response.isSuccessful) {
                    val myWrittenArticlesResponse = response.body()
                    myWrittenArticlesResponse?.let {
                        updateComment(it)
                    }
                } else {
                    // API 호출은 성공했지만 응답이 실패한 경우에 대한 처리
                    Log.e("Myfragment", "Failed to load my comment: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RetrofitClient2.MyWrittenCommentsResponse>, t: Throwable) {
                // 네트워크 호출 실패에 대한 처리
                Log.e("Myfragment", "Failed to load my comment", t)
            }
        })

    }

    private fun updateArticle(response: RetrofitClient2.MyWrittenArticlesResponse) {
        binding.myContentcnt1Tv.text = response.result.totalArticleCount.toString()
        //첫 번째 게시글
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.writerProfile)
            .into(binding.myProfile1Iv)
        binding.myUsernick1Tv.text = response.result.memberWrittenDtoList.writerName
        binding.myPosttitle1Tv.text = response.result.memberWrittenDtoList.title
        binding.myPostcontent1Tv.text = response.result.memberWrittenDtoList.content
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.images)
            .into(binding.myImage1Iv)
        binding.myLikecnt1Tv.text = response.result.memberWrittenDtoList.likeCount.toString()
        binding.myCommentcnt1Tv.text = response.result.memberWrittenDtoList.commentCount.toString()
        binding.myPosttime1Tv.text = response.result.memberWrittenDtoList.writtenDate.toString()

        //두 번째 게시글
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.writerProfile)
            .into(binding.myProfile2Iv)
        binding.myUsernick2Tv.text = response.result.memberWrittenDtoList.writerName
        binding.myPosttitle2Tv.text = response.result.memberWrittenDtoList.title
        binding.myPostcontent2Tv.text = response.result.memberWrittenDtoList.content
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.images)
            .into(binding.myImage2Iv)
        binding.myLikecnt2Tv.text = response.result.memberWrittenDtoList.likeCount.toString()
        binding.myCommentcnt2Tv.text = response.result.memberWrittenDtoList.commentCount.toString()
        binding.myPosttime2Tv.text = response.result.memberWrittenDtoList.writtenDate.toString()
    }

    private fun updateComment(response: RetrofitClient2.MyWrittenCommentsResponse) {
        binding.myContentcnt2Tv.text = response.result.totalArticleCount.toString()
        //첫 번째 게시글
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.writerProfile)
            .into(binding.myProfile3Iv)
        binding.myUsernick3Tv.text = response.result.memberWrittenDtoList.writerName
        binding.myPosttitle3Tv.text = response.result.memberWrittenDtoList.title
        binding.myPostcontent3Tv.text = response.result.memberWrittenDtoList.content
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.images)
            .into(binding.myImage3Iv)
        binding.myLikecnt3Tv.text = response.result.memberWrittenDtoList.likeCount.toString()
        binding.myCommentcnt3Tv.text = response.result.memberWrittenDtoList.commentCount.toString()
        binding.myPosttime3Tv.text = response.result.memberWrittenDtoList.writtenDate.toString()

        //두 번째 게시글
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.writerProfile)
            .into(binding.myProfile4Iv)
        binding.myUsernick4Tv.text = response.result.memberWrittenDtoList.writerName
        binding.myPosttitle4Tv.text = response.result.memberWrittenDtoList.title
        binding.myPostcontent4Tv.text = response.result.memberWrittenDtoList.content
        Glide.with(requireContext())
            .load(response.result.memberWrittenDtoList.images)
            .into(binding.myImage4Iv)
        binding.myLikecnt4Tv.text = response.result.memberWrittenDtoList.likeCount.toString()
        binding.myCommentcnt4Tv.text = response.result.memberWrittenDtoList.commentCount.toString()
        binding.myPosttime4Tv.text = response.result.memberWrittenDtoList.writtenDate.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}