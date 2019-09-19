package com.ayratis.frogogo

import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.ui.user_add.UserAddFragment
import com.ayratis.frogogo.ui.user_edit.UserEditFragment
import com.ayratis.frogogo.ui.user_list.UserListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object UserList : SupportAppScreen() {
        override fun getFragment() = UserListFragment()
    }

    object UserAdd : SupportAppScreen() {
        override fun getFragment() = UserAddFragment()
    }

    data class UserEdit (val user: User) : SupportAppScreen() {
        override fun getFragment() = UserEditFragment.create(user)
    }

}