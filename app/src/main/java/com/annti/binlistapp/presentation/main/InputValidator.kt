package com.annti.binlistapp.presentation.main

import androidx.lifecycle.MutableLiveData

class InputValidator(
    private val cardNumberErrorLiveData: MutableLiveData<CardNumberError>
) {

    fun isFieldValid(cardNumber: String): Boolean {
        val isLoginValid = when {
            cardNumber.isEmpty() -> {
                cardNumberErrorLiveData.value = CardNumberError.EMPTY
                false
            }
            cardNumber.length != 8 -> {
                cardNumberErrorLiveData.value = CardNumberError.NOT_VALID
                false
            }
            else -> {
                cardNumberErrorLiveData.value = CardNumberError.VALID
                true
            }
        }
        return isLoginValid
    }


}

enum class CardNumberError {
    EMPTY,
    NOT_VALID,
    VALID
}
