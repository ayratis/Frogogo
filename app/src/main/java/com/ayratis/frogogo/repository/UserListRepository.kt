package com.ayratis.frogogo.repository

import com.ayratis.frogogo.data.Api
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider
) {
    fun getUserList(): Single<List<User>> =
        api.getUserList()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}