package com.annti.binlistapp.data.mapper

import com.annti.binlistapp.data.model.CardInfo
import com.annti.binlistapp.entity.CardInfoEntity

fun CardInfo.toCardInfoEntity(number: String): CardInfoEntity =
    CardInfoEntity(
        cardNumber = number,
        scheme = this.scheme,
        type = this.type,
        brand = this.brand,
        nameCountry = this.country.name,
        countryEmoji = this.country.emoji,
        currency = this.country.currency,
        countryLatitude = this.country.latitude,
        countryLongitude = this.country.longitude,
        nameBank = this.bank.name,
        bankUrl = this.bank.url,
        bankPhone =  this.bank.phone,
        bankCity = this.bank.city,
        prepaid = this.prepaid,
        luhn = this.number.luhn,
        lengthCardNumber = this.number.length
    )