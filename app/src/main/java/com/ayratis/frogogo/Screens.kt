package com.ayratis.frogogo

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

    object UserEdit : SupportAppScreen() {
        override fun getFragment() = UserEditFragment()
    }

}