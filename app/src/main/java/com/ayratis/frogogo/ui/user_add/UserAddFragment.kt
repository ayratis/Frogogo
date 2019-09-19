package com.ayratis.frogogo.ui.user_add

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ayratis.frogogo.R
import com.ayratis.frogogo.extension.hideKeyboard
import com.ayratis.frogogo.extension.onTextChanges
import com.ayratis.frogogo.presentation.user_add.UserAddPresenter
import com.ayratis.frogogo.presentation.user_add.UserAddView
import com.ayratis.frogogo.ui._base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user_add.*

class UserAddFragment : BaseFragment(), UserAddView {

    override val layoutRes = R.layout.fragment_user_add

    @InjectPresenter
    lateinit var presenter: UserAddPresenter

    @ProvidePresenter
    fun providePresenter(): UserAddPresenter = scope.getInstance(UserAddPresenter::class.java)

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
        toolbar.isEnabled = enable
    }

    override fun hideKeyboard() {
        activity?.hideKeyboard()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
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
}