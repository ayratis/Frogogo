package com.ayratis.frogogo.entity

import com.google.gson.annotations.SerializedName

data class UserPostBody(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar_url") val avatarUrl: String = ""
)