package com.annti.binlistapp.data.db

import androidx.room.*
import com.annti.binlistapp.entity.CardInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT * FROM card_info")
    fun getResetCard(): Flow<List<CardInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCard(card: CardInfoEntity)

    @Delete
    suspend fun delete(card:CardInfoEntity)

}