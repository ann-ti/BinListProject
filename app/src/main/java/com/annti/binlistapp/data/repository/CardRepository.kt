package com.annti.binlistapp.data.repository

import com.annti.binlistapp.data.mapper.toCardInfoEntity
import com.annti.binlistapp.data.network.CardRemoteData
import com.annti.binlistapp.entity.CardInfoEntity
import com.annti.binlistapp.utils.Request
import com.annti.binlistapp.utils.RequestUtils.requestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val cardRemoteData: CardRemoteData
) {

    suspend fun getCardInfo(numberCard: String): Flow<Request<CardInfoEntity>> =
        requestFlow {
            val response = cardRemoteData.getCardInfo(numberCard)
            response.toCardInfoEntity(numberCard)
        }
}