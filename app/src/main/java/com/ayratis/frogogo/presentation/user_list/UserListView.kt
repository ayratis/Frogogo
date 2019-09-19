package com.ayratis.frogogo.presentation.user_list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ayratis.frogogo.entity.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserListView : MvpView {

    fun showLoading(show: Boolean)
    fun setUsersList(users: List<User>)
}