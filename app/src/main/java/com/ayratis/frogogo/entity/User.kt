package com.ayratis.frogogo.entity


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("last_name") val lastName: String
)