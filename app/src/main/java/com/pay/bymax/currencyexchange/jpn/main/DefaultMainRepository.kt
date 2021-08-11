package com.pay.bymax.currencyexchange.jpn.main

import com.pay.bymax.currencyexchange.jpn.data.CurrencyApi
import com.pay.bymax.currencyexchange.jpn.data.models.CurrencyResponse
import com.pay.bymax.currencyexchange.jpn.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRates(access_key:String,source: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(access_key,source)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}