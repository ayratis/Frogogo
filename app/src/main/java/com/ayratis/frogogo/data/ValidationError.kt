package com.ayratis.frogogo.data

import com.ayratis.frogogo.entity.ValidationErrorResponse
import java.lang.StringBuilder

class ValidationError(private val validationErrorResponse: ValidationErrorResponse) : RuntimeException() {

    override fun getLocalizedMessage(): String? {
        val firstNameMessage = StringBuilder()
        validationErrorResponse.firstNameErrors?.forEach {
            if (firstNameMessage.isNotEmpty()) firstNameMessage.append(", ")
            if (firstNameMessage.isEmpty()) firstNameMessage.append("First name ")
            firstNameMessage.append(it)
        }

        val secondtNameMessage = StringBuilder()
        validationErrorResponse.secondNameErrors?.forEach {
            if (secondtNameMessage.isNotEmpty()) secondtNameMessage.append(", ")
            if (secondtNameMessage.isEmpty()) secondtNameMessage.append("Last name ")
            secondtNameMessage.append(it)
        }

        val emailMessage = StringBuilder()
        validationErrorResponse.emailErrors?.forEach {
            if (emailMessage.isNotEmpty()) emailMessage.append(", ")
            if (emailMessage.isEmpty()) emailMessage.append("Email ")
            emailMessage.append(it)
        }

        val message = StringBuilder()
        message.append("$firstNameMessage. ")
        message.append("$secondtNameMessage. ")
        message.append("$emailMessage. ")

        return message.toString()
    }
}