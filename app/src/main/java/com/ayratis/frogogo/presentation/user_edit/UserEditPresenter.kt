package com.ayratis.frogogo.presentation.user_edit

import com.arellomobile.mvp.InjectViewState
import com.ayratis.frogogo.R
import com.ayratis.frogogo.di.UserToEdit
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.presentation._base.BasePresenter
import com.ayratis.frogogo.repository.UserEditRepository
import com.ayratis.frogogo.system.EmailValidatorProvider
import com.ayratis.frogogo.system.ResourceManager
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class UserEditPresenter @Inject constructor(
    @UserToEdit private val user: User,
    private val userEditRepository: UserEditRepository,
    private val router: Router,
    private val resourceManager: ResourceManager,
    private val emailValidator: EmailValidatorProvider
): BasePresenter<UserEditView>() {

    private var firstName: String = user.firstName
    private var secondName: String = user.lastName
    private var email: String = user.email

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showUserData(firstName, secondName, email)
    }

    fun onAcceptClick() {

        viewState.hideKeyboard()

        val isFirstNameValid = isFirstNameValid(firstName)
        val isSecondNameValid = isSecondNameValid(secondName)
        val isEmailValid = isEmailValid(email)

        if (isFirstNameValid && isSecondNameValid && isEmailValid) {

            userEditRepository.editUser(user.id, firstName, secondName, email)
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
                    {
                        viewState.enableUi(true)
                        viewState.showLoadingProgress(false)
                    }
                ).connect()
        }
    }

    fun onBackPressed() {
        router.exit()
    }

     fun onFirstNameChanged(firstName: String) {
        this.firstName = firstName
        viewState.showValidationError(UserEditView.Line.FIRST_NAME, false)
    }

    fun onSecondNameChanged(secondName: String) {
        this.secondName = secondName
        viewState.showValidationError(UserEditView.Line.SECOND_NAME, false)
    }

    fun onEmailChanged(email: String) {
        this.email = email
        viewState.showValidationError(UserEditView.Line.EMAIL, false)
    }

    private fun isFirstNameValid(firstName: String?): Boolean {
        return if (firstName.isNullOrEmpty()) {
            viewState.showValidationError(
                UserEditView.Line.FIRST_NAME,
                true,
                resourceManager.getString(R.string.should_not_be_empty)
            )
            false
        } else {
            viewState.showValidationError(UserEditView.Line.FIRST_NAME, false)
            true
        }
    }

    private fun isSecondNameValid(secondName: String?): Boolean {
        return if (secondName.isNullOrEmpty()) {
            viewState.showValidationError(
                UserEditView.Line.SECOND_NAME,
                true,
                resourceManager.getString(R.string.should_not_be_empty)
            )
            false
        } else {
            viewState.showValidationError(UserEditView.Line.SECOND_NAME, false)
            true
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return if (!emailValidator.isEmailValid(email)) {
            viewState.showValidationError(
                UserEditView.Line.EMAIL,
                true,
                resourceManager.getString(R.string.email_validation_error)
            )
            false
        } else {
            viewState.showValidationError(UserEditView.Line.EMAIL, false)
            true
        }
    }
}