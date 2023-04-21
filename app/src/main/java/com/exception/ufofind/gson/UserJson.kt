package com.vuforia.engine.native_sample.gson

import com.google.gson.annotations.SerializedName

data class UserJson(
    @SerializedName("name") val name: String,
    @SerializedName("coordinates") val coordinates: String,
    @SerializedName("job_title") val jobTitle: String
)
