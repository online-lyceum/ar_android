package com.vuforia.engine.native_sample.retrofit

import com.vuforia.engine.native_sample.gson.UsersListJson
import retrofit2.Call
import retrofit2.http.GET

interface GetUsersService {
    @GET("api/users")
    fun getUsers(): Call<UsersListJson>
}