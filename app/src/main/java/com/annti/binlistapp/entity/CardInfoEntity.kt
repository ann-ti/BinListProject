package com.annti.binlistapp.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "card_info")
data class CardInfoEntity(
    @PrimaryKey
    val cardNumber: String,
    val prepaid: Boolean?,
    val luhn: Boolean?,
    val lengthCardNumber: Int?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val nameCountry: String?,
    val countryEmoji: String?,
    val currency: String?,
    val countryLatitude: Double?,
    val countryLongitude: Double?,
    val nameBank: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String?
): Parcelable