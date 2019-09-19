package com.ayratis.frogogo.ui.user_edit

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.presentation.user_edit.UserEditPresenter
import com.ayratis.frogogo.presentation.user_edit.UserEditView
import com.ayratis.frogogo.ui._base.BaseFragment

class UserEditFragment : BaseFragment(), UserEditView {
    override val layoutRes = R.layout.fragment_user_add

    @InjectPresenter
    lateinit var presenter: UserEditPresenter

    @ProvidePresenter
    fun providePresenter(): UserEditPresenter = scope.getInstance(UserEditPresenter::class.java)
}