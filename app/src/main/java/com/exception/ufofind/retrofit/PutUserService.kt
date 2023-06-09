package com.vuforia.engine.native_sample.retrofit

import com.vuforia.engine.native_sample.gson.UserJson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface PutUserService {
    @PUT("api/user")
    fun putUser(@Body user: UserJson): Call<UserJson>
}