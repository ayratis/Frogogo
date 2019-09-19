package com.ayratis.frogogo.repository

import com.ayratis.frogogo.data.Api
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.entity.UserPost
import com.ayratis.frogogo.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class UserAddRepository @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider
) {
    fun addUser(firstName: String, secondName: String, email: String): Single<User> =
        api
            .addUser(
                UserPost.create(firstName, secondName, email)
            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}