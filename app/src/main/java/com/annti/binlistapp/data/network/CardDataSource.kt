package com.annti.binlistapp.data.network

import com.annti.binlistapp.data.model.CardInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CardDataSource {

    @GET("{card_number}")
    suspend fun getCardInfo(
        @Path("card_number") cardNumber: String
    ): CardInfo
}