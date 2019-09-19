package com.ayratis.frogogo.ui.user_edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.di.UserToEdit
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.extension.argument
import com.ayratis.frogogo.extension.hideKeyboard
import com.ayratis.frogogo.extension.onTextChanges
import com.ayratis.frogogo.presentation.user_edit.UserEditPresenter
import com.ayratis.frogogo.presentation.user_edit.UserEditView
import com.ayratis.frogogo.ui._base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_edit.*
import toothpick.Scope
import toothpick.config.Module

class UserEditFragment : BaseFragment(), UserEditView {
    override val layoutRes = R.layout.fragment_user_edit

    private val user: User by argument(ARG_USER)

    @InjectPresenter
    lateinit var presenter: UserEditPresenter

    @ProvidePresenter
    fun providePresenter(): UserEditPresenter = scope.getInstance(UserEditPresenter::class.java)

    override fun installModules(scope: Scope) {
        super.installModules(scope)
        scope.installModules(
            object : Module() {
                init {
                    bind(User::class.java).withName(UserToEdit::class.java).toInstance(user)
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.apply {
            setNavigationOnClickListener {
                presenter.onBackPressed()
            }
            inflateMenu(R.menu.menu_accept)
            setOnMenuItemClickListener {
                presenter.onAcceptClick()
                true
            }
        }

        emailEditText.setOnEditorActionListener { _, _, _ ->
            presenter.onAcceptClick()
            true
        }

        firstNameEditText.onTextChanges { text -> presenter.onFirstNameChanged(text) }
        secondNameEditText.onTextChanges { text -> presenter.onSecondNameChanged(text) }
        emailEditText.onTextChanges { text -> presenter.onEmailChanged(text) }
    }

    override fun showUserData(firstName: String, secondName: String, email: String) {
        firstNameEditText.setText(firstName)
        secondNameEditText.setText(secondName)
        emailEditText.setText(email)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showLoadingProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun enableUi(enable: Boolean) {
        firstNameEditText.isEnabled = enable
        secondNameEditText.isEnabled = enable
        emailEditText.isEnabled = enable
        toolbar.isEnabled = enable
    }

    override fun showValidationError(line: UserEditView.Line, show: Boolean, message: String?) {
        when (line) {
            UserEditView.Line.FIRST_NAME -> {
                firstNameInputLayout?.isErrorEnabled = show
                firstNameInputLayout?.error = message
            }
            UserEditView.Line.SECOND_NAME -> {
                secondNameInputLayout?.isErrorEnabled = show
                secondNameInputLayout?.error = message
            }
            UserEditView.Line.EMAIL -> {
                emailInputLayout?.isErrorEnabled = show
                emailInputLayout?.error = message
            }
        }
    }

    override fun hideKeyboard() {
        activity?.hideKeyboard()
    }

    companion object {
        private const val ARG_USER = "arg user"
        fun create(user: User) = UserEditFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_USER, user)
            }
        }
    }
}