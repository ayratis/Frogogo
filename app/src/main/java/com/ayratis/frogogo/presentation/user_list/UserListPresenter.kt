package com.ayratis.frogogo.presentation.user_list

import com.arellomobile.mvp.InjectViewState
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.presentation._base.BasePresenter
import com.ayratis.frogogo.repository.UserListRepository
import javax.inject.Inject

@InjectViewState
class UserListPresenter @Inject constructor(
    private val userListRepository: UserListRepository
): BasePresenter<UserListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUsers()
    }

    fun onRefresh() {
        loadUsers()
    }

    fun onItemClick(user: User) {

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
                {
                    viewState.showLoading(false)
                }
            ).connect()
    }
}