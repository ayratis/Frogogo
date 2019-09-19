package com.ayratis.frogogo.system

interface EmailValidatorProvider {
    fun isEmailValid(email: String): Boolean
}