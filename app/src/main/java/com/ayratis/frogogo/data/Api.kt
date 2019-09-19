package com.ayratis.frogogo.data

import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.entity.UserPost
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    @GET("users")
    fun getUserList(): Single<List<User>>

    @POST("users")
    fun addUser(@Body body: UserPost): Single<User>

    @PATCH("users/{id}")
    fun editUser(@Path("id") id: Int, @Body body: UserPost): Single<User>
}