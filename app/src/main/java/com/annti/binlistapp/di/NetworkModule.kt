package com.annti.binlistapp.di

import com.annti.binlistapp.data.network.CardDataSource
import com.annti.binlistapp.data.network.CardRemoteData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://lookup.binlist.net/")
        .build()

    @Provides
    @Singleton
    fun provideCardDataSource(retrofit: Retrofit): CardDataSource =
        retrofit.create(CardDataSource::class.java)

    @Provides
    @Singleton
    fun provideCardRemoteData(mainService: CardDataSource): CardRemoteData =
        CardRemoteData(mainService)
}