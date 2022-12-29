package com.annti.binlistapp.domain

import com.annti.binlistapp.data.db.CardDao
import com.annti.binlistapp.data.repository.CardRepository
import com.annti.binlistapp.entity.CardInfoEntity
import com.annti.binlistapp.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardUseCase @Inject constructor(
    private val cardRepository: CardRepository,
    private val cardDao: CardDao
) {
    suspend fun getCardInfo(numberCard: String): Flow<Request<CardInfoEntity>> {
        val result = cardRepository.getCardInfo(numberCard)
        result.collect {
            if (it is Request.Success) {
                cardDao.saveCard(it.data)
            }
        }
        return result
    }

    fun getResetCard(): Flow<List<CardInfoEntity>> =
        cardDao.getResetCard()

    suspend fun delete(card: CardInfoEntity) {
        cardDao.delete(card)
    }

}