package com.annti.binlistapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardInfo(
    val number: NumberData,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryData,
    val bank: BankData
)

@JsonClass(generateAdapter = true)
data class NumberData(
    val length: Int?,
    val luhn: Boolean?
)

@JsonClass(generateAdapter = true)
data class CountryData(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Double?,
    val longitude: Double?,
)

@JsonClass(generateAdapter = true)
data class BankData(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)
