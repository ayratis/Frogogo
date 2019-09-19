package com.ayratis.frogogo.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("id") val id: Long,
    @SerializedName("last_name") val lastName: String
) : Parcelable