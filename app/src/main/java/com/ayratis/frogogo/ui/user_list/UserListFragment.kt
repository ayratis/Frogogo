package com.ayratis.frogogo.ui.user_list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.presentation.user_list.UserListPresenter
import com.ayratis.frogogo.presentation.user_list.UserListView
import com.ayratis.frogogo.ui._base.BaseFragment
import com.ayratis.frogogo.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : BaseFragment(), UserListView {

    override val layoutRes = R.layout.fragment_user_list

    @InjectPresenter
    lateinit var presenter: UserListPresenter

    @ProvidePresenter
    fun providePresenter(): UserListPresenter = scope.getInstance(UserListPresenter::class.java)

    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter { user -> presenter.onItemClick(user) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener { presenter.onRefresh() }
        addFab.setOnClickListener { presenter.onAddFabClick() }
    }

    override fun showLoading(show: Boolean) {
        postViewAction { swipeRefreshLayout.isRefreshing = show }
    }

    override fun setUsersList(users: List<User>) {
        userListAdapter.setItems(users)
    }

}