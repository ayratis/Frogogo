package com.ayratis.frogogo.data

import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.entity.UserPost
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    @GET("users.json")
    fun getUserList(): Single<List<User>>

    @POST("users.json")
    fun addUser(@Body body: UserPost): Single<User>

    @PATCH("users/{id}.json")
    fun editUser(@Path("id") id: Long, @Body body: UserPost): Single<User>
}