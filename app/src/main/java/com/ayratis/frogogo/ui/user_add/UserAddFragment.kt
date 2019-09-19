package com.ayratis.frogogo.ui.user_add

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.presentation.user_add.UserAddPresenter
import com.ayratis.frogogo.presentation.user_add.UserAddView
import com.ayratis.frogogo.ui._base.BaseFragment

class UserAddFragment : BaseFragment(), UserAddView {
    override val layoutRes = R.layout.fragment_user_add

    @InjectPresenter
    lateinit var presenter: UserAddPresenter

    @ProvidePresenter
    fun providePresenter(): UserAddPresenter = scope.getInstance(UserAddPresenter::class.java)
}