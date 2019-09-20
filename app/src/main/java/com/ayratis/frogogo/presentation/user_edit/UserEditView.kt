package com.ayratis.frogogo.presentation.user_edit

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserEditView : MvpView {

    enum class Line {
        FIRST_NAME,
        SECOND_NAME,
        EMAIL
    }

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showUserData(firstName: String, secondName: String, email: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showValidationError(line: Line, show: Boolean, message: String? = null)

    fun showLoadingProgress(show: Boolean)
    fun enableUi(enable: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideKeyboard()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String?)
}