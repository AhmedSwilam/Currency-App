package com.pay.bymax.currencyexchange.jpn.main

import com.pay.bymax.currencyexchange.jpn.data.models.CurrencyResponse
import com.pay.bymax.currencyexchange.jpn.util.Resource

interface MainRepository {

    suspend fun getRates(access_key:String,source: String): Resource<CurrencyResponse>

}