package com.annti.binlistapp.data.network

import com.annti.binlistapp.data.model.CardInfo
import javax.inject.Inject

class CardRemoteData @Inject constructor(
    private val cardDataSource: CardDataSource
) {

    suspend fun getCardInfo(numberCard: String): CardInfo =
        cardDataSource.getCardInfo(numberCard)

}