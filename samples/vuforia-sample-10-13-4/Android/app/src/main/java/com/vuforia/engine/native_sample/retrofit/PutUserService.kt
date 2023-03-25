package com.vuforia.engine.native_sample.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface PutUserService {
    @PUT("api/user")
    suspend fun putUser(@Body requestBody: RequestBody): Response<ResponseBody>
}