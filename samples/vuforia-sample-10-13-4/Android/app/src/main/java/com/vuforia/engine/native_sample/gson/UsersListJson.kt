package com.vuforia.engine.native_sample.gson

import com.google.gson.annotations.SerializedName

data class UsersListJson(
    @SerializedName("users") val users: List<UserJson>
)
