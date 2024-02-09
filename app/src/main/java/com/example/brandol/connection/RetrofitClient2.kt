package com.example.brandol.connection

import com.google.gson.annotations.SerializedName

class RetrofitClient2 {
    data class RequestLogin(
        @SerializedName("email")
        val email: String,
        @SerializedName("name")
        val name: String
    )

    data class ResponseLogin(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: LoginResult
    )

    data class LoginResult(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )

    data class RequestSignup(
        @SerializedName("email")
        val email: String,
        @SerializedName("termsIdList")
        val termsIdList: List<Long>,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("age")
        val age: Int
    )

    data class ResponseSignup(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: SignupResult
    )

    data class SignupResult(
        @SerializedName("memberId")
        val memberId: Long,
        @SerializedName("signUp")
        val signUp: Boolean
    )

    data class ResponseMyItem(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: ItemResult
    )

    data class ItemResult(
        @SerializedName("myItemId")
        val myItemId: Long,
        @SerializedName("itemId")
        val itemId: Long,
        @SerializedName("brandId")
        val brandId: Long,
        @SerializedName("brandName")
        val brandName : String,
        @SerializedName("itemName")
        val itemName : String,
        @SerializedName("part")
        val part : String,
        @SerializedName("description")
        val description : String,
        @SerializedName("image")
        val image : String,
        @SerializedName("price")
        val price : Int,
        @SerializedName("createdAt")
        val createdAt : String,
        @SerializedName("wearing")
        val wearing : Boolean,
    )

}