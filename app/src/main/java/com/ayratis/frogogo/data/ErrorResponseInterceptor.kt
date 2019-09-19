package com.ayratis.frogogo.data

import com.ayratis.frogogo.entity.ValidationErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorResponseInterceptor (private val gson: Gson) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code() == 422) {
            try {
                val error =
                    gson.fromJson(response.body()?.charStream(), ValidationErrorResponse::class.java)
                throw ValidationError(error)
            } catch (e: JsonSyntaxException) {
                return response
            }
        }

        return response
    }

}