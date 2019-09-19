package com.ayratis.frogogo.system.email_validation

class EmailValidator : EmailValidatorProvider {
    override fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}