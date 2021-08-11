package com.pay.bymax.currencyexchange.jpn.data

import com.pay.bymax.currencyexchange.jpn.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/live")
    suspend fun getRates(
        @Query("access_key") access_key:String,
        @Query("source") source: String
    ): Response<CurrencyResponse>
}