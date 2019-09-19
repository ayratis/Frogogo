package com.ayratis.frogogo.system.email_validation

interface EmailValidatorProvider {
    fun isEmailValid(email: String): Boolean
}