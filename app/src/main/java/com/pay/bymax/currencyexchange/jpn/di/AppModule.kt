package com.pay.bymax.currencyexchange.jpn.di

import com.pay.bymax.currencyexchange.jpn.data.CurrencyApi
import com.pay.bymax.currencyexchange.jpn.main.DefaultMainRepository
import com.pay.bymax.currencyexchange.jpn.main.MainRepository
import com.pay.bymax.currencyexchange.jpn.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://api.currencylayer.com"

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}