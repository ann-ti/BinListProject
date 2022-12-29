package com.annti.binlistapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.annti.binlistapp.entity.CardInfoEntity

@Database(
    entities = [
        CardInfoEntity::class
    ], version = CardDatabase.DB_VERSION
)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        const val DB_VERSION = 1
        const val DB_MAME = "card_database"
    }
}