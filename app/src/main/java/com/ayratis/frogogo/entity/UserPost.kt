package com.ayratis.frogogo.entity

import com.google.gson.annotations.SerializedName

data class UserPost(
    @SerializedName("user") val userPostBody: UserPostBody
) {
    companion object {
        fun create(firstName: String, lastName: String, email: String): UserPost {
            return UserPost(
                UserPostBody(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            )
        }
    }
}