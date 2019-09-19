package com.ayratis.frogogo.ui.user_list

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.presentation.user_list.UserListPresenter
import com.ayratis.frogogo.presentation.user_list.UserListView
import com.ayratis.frogogo.ui._base.BaseFragment

class UserListFragment : BaseFragment(), UserListView {
    override val layoutRes = R.layout.fragment_user_list

    @InjectPresenter
    lateinit var presenter: UserListPresenter

    @ProvidePresenter
    fun providePresenter(): UserListPresenter = scope.getInstance(UserListPresenter::class.java)

}