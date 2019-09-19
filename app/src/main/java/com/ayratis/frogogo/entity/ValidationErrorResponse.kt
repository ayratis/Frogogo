package com.ayratis.frogogo.entity

import com.google.gson.annotations.SerializedName

data class ValidationErrorResponse(
    @SerializedName ("first_name") var firstNameErrors: List<String>?,
    @SerializedName ("last_name") var secondNameErrors: List<String>?,
    @SerializedName ("email") var emailErrors: List<String>?
)