package com.vuforia.engine.native_sample.retrofit

import android.util.Log
import com.vuforia.engine.native_sample.CantCreateRetrofitRequestException
import com.vuforia.engine.native_sample.LOG_TAG_RETROFIT_ON_FAILURE
import com.vuforia.engine.native_sample.gson.UsersListJson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val BASE_URL = "https://ufo.lyceumland.ru"

    private var retrofit: Retrofit? = null

    private fun createClient() {
        if(retrofit==null) {
            val okHttpClient = OkHttpClient.Builder()
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    fun getUsers(listener: (UsersListJson?) -> Unit) {
        createClient()
        val service = retrofit?.create(GetUsersService::class.java)
        val call = service?.getUsers() ?: throw CantCreateRetrofitRequestException()
        call.enqueue(object: Callback<UsersListJson> {
            override fun onResponse(call: Call<UsersListJson>, response: Response<UsersListJson>) {
                listener(response.body())
            }

            override fun onFailure(call: Call<UsersListJson>, t: Throwable) {
                Log.e(LOG_TAG_RETROFIT_ON_FAILURE, t.toString())
                listener(null)
            }
        })
    }


}