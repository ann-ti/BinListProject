package com.annti.binlistapp.di

import android.content.Context
import androidx.room.Room
import com.annti.binlistapp.data.db.CardDao
import com.annti.binlistapp.data.db.CardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCardDao(appDatabase: CardDatabase): CardDao {
        return appDatabase.cardDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CardDatabase {
        return Room.databaseBuilder(
            appContext,
            CardDatabase::class.java,
            CardDatabase.DB_MAME
        ).build()
    }
}