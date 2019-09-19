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

    fun showValidationError(line: Line, show: Boolean, message: String? = null)
    fun showLoadingProgress(show: Boolean)
    fun enableUi(enable: Boolean)
    fun hideKeyboard()
}