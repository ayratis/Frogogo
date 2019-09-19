package com.ayratis.frogogo.presentation.user_add

import com.arellomobile.mvp.InjectViewState
import com.ayratis.frogogo.R
import com.ayratis.frogogo.presentation._base.BasePresenter
import com.ayratis.frogogo.repository.UserAddRepository
import com.ayratis.frogogo.system.ErrorHandler
import com.ayratis.frogogo.system.email_validation.EmailValidatorProvider
import com.ayratis.frogogo.system.ResourceManager
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class UserAddPresenter @Inject constructor(
    private val userAddRepository: UserAddRepository,
    private val router: Router,
    private val emailValidar: EmailValidatorProvider,
    private val resourceManager: ResourceManager,
    private val errorHandler: ErrorHandler
) : BasePresenter<UserAddView>() {

    private var firstName: String = ""
    private var secondName: String = ""
    private var email: String = ""

    fun onAcceptClick() {

        viewState.hideKeyboard()

        val isFirstNameValid = isFirstNameValid(firstName)
        val isSecondNameValid = isSecondNameValid(secondName)
        val isEmailValid = isEmailValid(email)

        if (isFirstNameValid && isSecondNameValid && isEmailValid) {
            userAddRepository.addUser(firstName, secondName, email)
                .doOnSubscribe {
                    viewState.enableUi(false)
                    viewState.showLoadingProgress(true)
                }
                .subscribe(
                    {
                        viewState.enableUi(true)
                        viewState.showLoadingProgress(false)
                        viewState.showSuccessDialog()
                    },
                    { error ->
                        viewState.enableUi(true)
                        viewState.showLoadingProgress(false)
                        errorHandler.proceed(error) { viewState.showMessage(it) }
                    }
                ).connect()
        }
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onFirstNameChanged(firstName: String) {
        this.firstName = firstName
        viewState.showValidationError(UserAddView.Line.FIRST_NAME, false)
    }

    fun onSecondNameChanged(secondName: String) {
        this.secondName = secondName
        viewState.showValidationError(UserAddView.Line.SECOND_NAME, false)
    }

    fun onEmailChanged(email: String) {
        this.email = email
        viewState.showValidationError(UserAddView.Line.EMAIL, false)
    }

    private fun isFirstNameValid(firstName: String?): Boolean {
        return if (firstName.isNullOrEmpty()) {
            viewState.showValidationError(
                UserAddView.Line.FIRST_NAME,
                true,
                resourceManager.getString(R.string.should_not_be_empty)
            )
            false
        } else {
            viewState.showValidationError(UserAddView.Line.FIRST_NAME, false)
            true
        }
    }

    private fun isSecondNameValid(secondName: String?): Boolean {
        return if (secondName.isNullOrEmpty()) {
            viewState.showValidationError(
                UserAddView.Line.SECOND_NAME,
                true,
                resourceManager.getString(R.string.should_not_be_empty)
            )
            false
        } else {
            viewState.showValidationError(UserAddView.Line.SECOND_NAME, false)
            true
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (!emailValidar.isEmailValid(email)) {
            viewState.showValidationError(
                UserAddView.Line.EMAIL,
                true,
                resourceManager.getString(R.string.email_validation_error)
            )
            false
        } else {
            viewState.showValidationError(UserAddView.Line.EMAIL, false)
            true
        }
    }

}