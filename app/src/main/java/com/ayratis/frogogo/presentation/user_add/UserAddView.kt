package com.ayratis.frogogo.presentation.user_add

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserAddView : MvpView {

    enum class Line {
        FIRST_NAME,
        SECOND_NAME,
        EMAIL
    }

    fun showValidationError(line: Line, show: Boolean, message: String? = null)
    fun showLoadingProgress(show: Boolean)
    fun enableUi(enable: Boolean)
    fun hideKeyboard()
    fun showSuccessDialog()
}