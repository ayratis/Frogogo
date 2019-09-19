package com.ayratis.frogogo.system

class EmailValidator : EmailValidatorProvider {
    override fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}