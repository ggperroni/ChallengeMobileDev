package com.ggperroni.challengemobiledev.data.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String
)