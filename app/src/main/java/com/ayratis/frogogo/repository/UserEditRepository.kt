package com.ayratis.frogogo.repository

import com.ayratis.frogogo.data.Api
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.entity.UserPost
import com.ayratis.frogogo.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class UserEditRepository @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider
) {
    fun editUser(id: Long, firstName: String, secondName: String, email: String): Single<User> =
        api
            .editUser(
                id,
                UserPost.create(firstName, secondName, email)
            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}