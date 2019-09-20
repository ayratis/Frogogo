package com.ayratis.frogogo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ayratis.frogogo.R
import com.ayratis.frogogo.Screens
import com.ayratis.frogogo.di.DI
import com.ayratis.frogogo.ui._base.BaseFragment
import com.ayratis.frogogo.ui.user_edit.UserEditFragment
import com.ayratis.frogogo.ui.user_list.UserListFragment
import kotlinx.android.synthetic.main.layout_container.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {

            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)

                if (command is Forward &&
                    currentFragment is UserListFragment &&
                    nextFragment is UserEditFragment
                ) {
                    setupSharedElementForCalendarToSchedule(currentFragment, fragmentTransaction)
                }
            }
        }
    }

    private fun setupSharedElementForCalendarToSchedule(
        userListFragment: UserListFragment,
        fragmentTransaction: FragmentTransaction
    ) {
        val view = userListFragment.sharedView
        if (view != null) {
            val transitionName = ViewCompat.getTransitionName(view)
            if (transitionName != null) {
                fragmentTransaction.addSharedElement(view, transitionName)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)

        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.UserList)
        }
    }

    fun showBackNavButton(show: Boolean) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(show)
            if (show) setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
        return true
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}
