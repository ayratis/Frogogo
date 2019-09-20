package com.ayratis.frogogo.ui.user_list

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.presentation.user_list.UserListPresenter
import com.ayratis.frogogo.presentation.user_list.UserListView
import com.ayratis.frogogo.ui.AppActivity
import com.ayratis.frogogo.ui._base.BaseFragment
import com.ayratis.frogogo.ui._base.RevealAnimationSetting
import com.ayratis.frogogo.ui.adapter.UserListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : BaseFragment(), UserListView {

    override val layoutRes = R.layout.fragment_user_list

    @InjectPresenter
    lateinit var presenter: UserListPresenter

    @ProvidePresenter
    fun providePresenter(): UserListPresenter = scope.getInstance(UserListPresenter::class.java)

    var sharedView: View? = null
        private set

    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter { user, view ->
            sharedView = view
            presenter.onItemClick(user)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.setTitle(R.string.user_list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppActivity)?.showBackNavButton(false)
        activity?.setTitle(R.string.user_list)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener { presenter.onRefresh() }
        addFab.setOnClickListener {

            presenter.onAddFabClick(
                RevealAnimationSetting(
                    (it.x + it.width / 2).toInt(),
                    (it.y + it.height / 2).toInt(),
                    view?.width ?: 0,
                    view?.height ?: 0
                )
            )
        }

        postponeEnterTransition()
        recyclerView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                recyclerView.removeOnLayoutChangeListener(this)
                startPostponedEnterTransition()
            }
        })
    }

    override fun showLoading(show: Boolean) {
        postViewAction { swipeRefreshLayout.isRefreshing = show }
    }

    override fun setUsersList(users: List<User>) {
        userListAdapter.setItems(users)
    }

    override fun showMessage(message: String?) {
        if (message == null) return
        view?.run {
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
        }
    }

}