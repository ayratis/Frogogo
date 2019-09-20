package com.ayratis.frogogo.ui.user_add

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.extension.*
import com.ayratis.frogogo.presentation.user_add.UserAddPresenter
import com.ayratis.frogogo.presentation.user_add.UserAddView
import com.ayratis.frogogo.ui.AppActivity
import com.ayratis.frogogo.ui._base.AnimationUtils
import com.ayratis.frogogo.ui._base.BaseFragment
import com.ayratis.frogogo.ui._base.RevealAnimationSetting
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user_add.*

class UserAddFragment : BaseFragment(), UserAddView {

    override val layoutRes = R.layout.fragment_user_add

    private val revealAnimationSetting: RevealAnimationSetting by argument(ARG_REVEAL_ANIM_SETTINGS)

    @InjectPresenter
    lateinit var presenter: UserAddPresenter

    @ProvidePresenter
    fun providePresenter(): UserAddPresenter = scope.getInstance(UserAddPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationUtils.registerCircularRevealAnimation(
            context,
            view,
            revealAnimationSetting,
            view.context.color(R.color.colorAccent),
            view.context.color(R.color.white)
        )
    }

//    private fun dismiss(listener: () -> Unit) {
//        context?.run {
//            AnimationUtils.startCircularRevealExitAnimation(
//                this,
//                view,
//                revealAnimationSetting,
//                color(R.color.white),
//                color(R.color.colorAccent),
//                listener
//            )
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_accept, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_accept) presenter.onAcceptClick()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppActivity)?.showBackNavButton(true)
        activity?.setTitle(R.string.add)

        emailEditText.setOnEditorActionListener { _, _, _ ->
            presenter.onAcceptClick()
            true
        }

        firstNameEditText.onTextChanges { text -> presenter.onFirstNameChanged(text) }
        secondNameEditText.onTextChanges { text -> presenter.onSecondNameChanged(text) }
        emailEditText.onTextChanges { text -> presenter.onEmailChanged(text) }
    }

    override fun showValidationError(line: UserAddView.Line, show: Boolean, message: String?) {
        when (line) {
            UserAddView.Line.FIRST_NAME -> {
                firstNameInputLayout?.isErrorEnabled = show
                firstNameInputLayout?.error = message
            }
            UserAddView.Line.SECOND_NAME -> {
                secondNameInputLayout?.isErrorEnabled = show
                secondNameInputLayout?.error = message
            }
            UserAddView.Line.EMAIL -> {
                emailInputLayout?.isErrorEnabled = show
                emailInputLayout?.error = message
            }
        }
    }

    override fun showLoadingProgress(show: Boolean) {
        progressBar?.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun enableUi(enable: Boolean) {
        firstNameEditText.isEnabled = enable
        secondNameEditText.isEnabled = enable
        emailEditText.isEnabled = enable
    }

    override fun hideKeyboard() {
        activity?.hideKeyboard()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
//        dismiss{ presenter.onBackPressed() }
    }

    override fun showSuccessDialog() {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.success_add_message))
            .setPositiveButton(getString(R.string.ok)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    override fun showMessage(message: String?) {
        if (message == null) return
        view?.run {
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val ARG_REVEAL_ANIM_SETTINGS = "reveal anim settings"
        fun create(revealAnimationSetting: RevealAnimationSetting) = UserAddFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_REVEAL_ANIM_SETTINGS, revealAnimationSetting)
            }
        }
    }
}
