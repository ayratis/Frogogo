package com.ayratis.frogogo.presentation.user_list

import com.arellomobile.mvp.InjectViewState
import com.ayratis.frogogo.Screens
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.presentation._base.BasePresenter
import com.ayratis.frogogo.repository.UserListRepository
import com.ayratis.frogogo.system.ErrorHandler
import com.ayratis.frogogo.ui._base.RevealAnimationSetting
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class UserListPresenter @Inject constructor(
    private val userListRepository: UserListRepository,
    private val router: Router,
    private val errorHandler: ErrorHandler
): BasePresenter<UserListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUsers()
    }

    var animateFab: Boolean = false

    fun onRefresh() {
        loadUsers()
    }

    fun onItemClick(user: User) {
        router.navigateTo(Screens.UserEdit(user))
    }

    fun onAddFabClick(revealAnimationSetting: RevealAnimationSetting) {
        router.navigateTo(Screens.UserAdd(revealAnimationSetting))
    }

    private fun loadUsers() {
        userListRepository.getUserList()
            .doOnSubscribe {
                viewState.showLoading(true)
            }
            .subscribe(
                { users ->
                    viewState.setUsersList(users)
                    viewState.showLoading(false)
                },
                { error ->
                    viewState.showLoading(false)
                    errorHandler.proceed(error) { viewState.showMessage(it) }
                }
            ).connect()
    }
}