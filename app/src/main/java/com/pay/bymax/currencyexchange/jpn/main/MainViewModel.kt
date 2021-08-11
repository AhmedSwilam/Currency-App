package com.pay.bymax.currencyexchange.jpn.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pay.bymax.currencyexchange.jpn.data.models.Quotes
import com.pay.bymax.currencyexchange.jpn.util.DispatcherProvider
import com.pay.bymax.currencyexchange.jpn.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Math.round

class MainViewModel constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        source: String,
        allCurrencyList: Unit
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRates(amountStr,source)) {
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.quotes
                    val rate = getRateForCurrency(source, rates)
                    if (rate == null) {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $source"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Quotes) = when (currency) {
        "USDAED" -> rates.USDAED
        "USDAFN" -> rates.USDAFN
        "USDALL" -> rates.USDALL
        "USDAMD" -> rates.USDAMD
        "USDANG" -> rates.USDANG
        "USDAOA" -> rates.USDAOA
        "USDARS" -> rates.USDARS
        "USDAWG" -> rates.USDAWG
        "USDAZN" -> rates.USDAZN
        "USDBAM" -> rates.USDBAM
        "USDBBD" -> rates.USDBBD
        "USDBDT" -> rates.USDBDT
        "USDBGN" -> rates.USDBGN
        "USDBHD" -> rates.USDBHD
        "USDBIF" -> rates.USDBIF

//        @SerializedName("USDAMD") var USDAMD : Double,
//        @SerializedName("USDANG") var USDANG : Double,
//        @SerializedName("USDAOA") var USDAOA : Double,
//        @SerializedName("USDARS") var USDARS : Double,
//        @SerializedName("USDAUD") var USDAUD : Double,
//        @SerializedName("USDAWG") var USDAWG : Double,
//        @SerializedName("USDAZN") var USDAZN : Double,
//        @SerializedName("USDBAM") var USDBAM : Double,
//        @SerializedName("USDBBD") var USDBBD : Double,
//        @SerializedName("USDBDT") var USDBDT : Double,
//        @SerializedName("USDBGN") var USDBGN : Double,
//        @SerializedName("USDBHD") var USDBHD : Double,
//        @SerializedName("USDBIF") var USDBIF : Double,
//        @SerializedName("USDBMD") var USDBMD : Int,
//        @SerializedName("USDBND") var USDBND : Double,
//        @SerializedName("USDBOB") var USDBOB : Double,
//        @SerializedName("USDBRL") var USDBRL : Double,
//        @SerializedName("USDBSD") var USDBSD : Double,
//        @SerializedName("USDBTC") var USDBTC : Double,
//        @SerializedName("USDBTN") var USDBTN : Double,
//        @SerializedName("USDBWP") var USDBWP : Double,
//        @SerializedName("USDBYN") var USDBYN : Double,
//        @SerializedName("USDBYR") var USDBYR : Int,
//        @SerializedName("USDBZD") var USDBZD : Double,
//        @SerializedName("USDCAD") var USDCAD : Double,
//        @SerializedName("USDCDF") var USDCDF : Double,
//        @SerializedName("USDCHF") var USDCHF : Double,
//        @SerializedName("USDCLF") var USDCLF : Double,
//        @SerializedName("USDCLP") var USDCLP : Double,
//        @SerializedName("USDCNY") var USDCNY : Double,
//        @SerializedName("USDCOP") var USDCOP : Double,
//        @SerializedName("USDCRC") var USDCRC : Double,
//        @SerializedName("USDCUC") var USDCUC : Int,
//        @SerializedName("USDCUP") var USDCUP : Double,
//        @SerializedName("USDCVE") var USDCVE : Double,
//        @SerializedName("USDCZK") var USDCZK : Double,
//        @SerializedName("USDDJF") var USDDJF : Double,
//        @SerializedName("USDDKK") var USDDKK : Double,
//        @SerializedName("USDDOP") var USDDOP : Double,
//        @SerializedName("USDDZD") var USDDZD : Double,
//        @SerializedName("USDEGP") var USDEGP : Double,
//        @SerializedName("USDERN") var USDERN : Double,
//        @SerializedName("USDETB") var USDETB : Double,
//        @SerializedName("USDEUR") var USDEUR : Double,
//        @SerializedName("USDFJD") var USDFJD : Double,
//        @SerializedName("USDFKP") var USDFKP : Double,
//        @SerializedName("USDGBP") var USDGBP : Double,
//        @SerializedName("USDGEL") var USDGEL : Double,
//        @SerializedName("USDGGP") var USDGGP : Double,
//        @SerializedName("USDGHS") var USDGHS : Double,
//        @SerializedName("USDGIP") var USDGIP : Double,
//        @SerializedName("USDGMD") var USDGMD : Double,
//        @SerializedName("USDGNF") var USDGNF : Double,
//        @SerializedName("USDGTQ") var USDGTQ : Double,
//        @SerializedName("USDGYD") var USDGYD : Double,
//        @SerializedName("USDHKD") var USDHKD : Double,
//        @SerializedName("USDHNL") var USDHNL : Double,
//        @SerializedName("USDHRK") var USDHRK : Double,
//        @SerializedName("USDHTG") var USDHTG : Double,
//        @SerializedName("USDHUF") var USDHUF : Double,
//        @SerializedName("USDIDR") var USDIDR : Double,
//        @SerializedName("USDILS") var USDILS : Double,
//        @SerializedName("USDIMP") var USDIMP : Double,
//        @SerializedName("USDINR") var USDINR : Double,
//        @SerializedName("USDIQD") var USDIQD : Double,
//        @SerializedName("USDIRR") var USDIRR : Double,
//        @SerializedName("USDISK") var USDISK : Double,
//        @SerializedName("USDJEP") var USDJEP : Double,
//        @SerializedName("USDJMD") var USDJMD : Double,
//        @SerializedName("USDJOD") var USDJOD : Double,
//        @SerializedName("USDJPY") var USDJPY : Double,
//        @SerializedName("USDKES") var USDKES : Double,
//        @SerializedName("USDKGS") var USDKGS : Double,
//        @SerializedName("USDKHR") var USDKHR : Double,
//        @SerializedName("USDKMF") var USDKMF : Double,
//        @SerializedName("USDKPW") var USDKPW : Double,
//        @SerializedName("USDKRW") var USDKRW : Double,
//        @SerializedName("USDKWD") var USDKWD : Double,
//        @SerializedName("USDKYD") var USDKYD : Double,
//        @SerializedName("USDKZT") var USDKZT : Double,
//        @SerializedName("USDLAK") var USDLAK : Double,
//        @SerializedName("USDLBP") var USDLBP : Double,
//        @SerializedName("USDLKR") var USDLKR : Double,
//        @SerializedName("USDLRD") var USDLRD : Double,
//        @SerializedName("USDLSL") var USDLSL : Double,
//        @SerializedName("USDLTL") var USDLTL : Double,
//        @SerializedName("USDLVL") var USDLVL : Double,
//        @SerializedName("USDLYD") var USDLYD : Double,
//        @SerializedName("USDMAD") var USDMAD : Double,
//        @SerializedName("USDMDL") var USDMDL : Double,
//        @SerializedName("USDMGA") var USDMGA : Double,
//        @SerializedName("USDMKD") var USDMKD : Double,
//        @SerializedName("USDMMK") var USDMMK : Double,
//        @SerializedName("USDMNT") var USDMNT : Double,
//        @SerializedName("USDMOP") var USDMOP : Double,
//        @SerializedName("USDMRO") var USDMRO : Double,
//        @SerializedName("USDMUR") var USDMUR : Double,
//        @SerializedName("USDMVR") var USDMVR : Double,
//        @SerializedName("USDMWK") var USDMWK : Double,
//        @SerializedName("USDMXN") var USDMXN : Double,
//        @SerializedName("USDMYR") var USDMYR : Double,
//        @SerializedName("USDMZN") var USDMZN : Double,
//        @SerializedName("USDNAD") var USDNAD : Double,
//        @SerializedName("USDNGN") var USDNGN : Double,
//        @SerializedName("USDNIO") var USDNIO : Double,
//        @SerializedName("USDNOK") var USDNOK : Double,
//        @SerializedName("USDNPR") var USDNPR : Double,
//        @SerializedName("USDNZD") var USDNZD : Double,
//        @SerializedName("USDOMR") var USDOMR : Double,
//        @SerializedName("USDPAB") var USDPAB : Double,
//        @SerializedName("USDPEN") var USDPEN : Double,
//        @SerializedName("USDPGK") var USDPGK : Double,
//        @SerializedName("USDPHP") var USDPHP : Double,
//        @SerializedName("USDPKR") var USDPKR : Double,
//        @SerializedName("USDPLN") var USDPLN : Double,
//        @SerializedName("USDPYG") var USDPYG : Double,
//        @SerializedName("USDQAR") var USDQAR : Double,
//        @SerializedName("USDRON") var USDRON : Double,
//        @SerializedName("USDRSD") var USDRSD : Double,
//        @SerializedName("USDRUB") var USDRUB : Double,
//        @SerializedName("USDRWF") var USDRWF : Double,
//        @SerializedName("USDSAR") var USDSAR : Double,
//        @SerializedName("USDSBD") var USDSBD : Double,
//        @SerializedName("USDSCR") var USDSCR : Double,
//        @SerializedName("USDSDG") var USDSDG : Double,
//        @SerializedName("USDSEK") var USDSEK : Double,
//        @SerializedName("USDSGD") var USDSGD : Double,
//        @SerializedName("USDSHP") var USDSHP : Double,
//        @SerializedName("USDSLL") var USDSLL : Double,
//        @SerializedName("USDSOS") var USDSOS : Double,
//        @SerializedName("USDSRD") var USDSRD : Double,
//        @SerializedName("USDSTD") var USDSTD : Double,
//        @SerializedName("USDSVC") var USDSVC : Double,
//        @SerializedName("USDSYP") var USDSYP : Double,
//        @SerializedName("USDSZL") var USDSZL : Double,
//        @SerializedName("USDTHB") var USDTHB : Double,
//        @SerializedName("USDTJS") var USDTJS : Double,
//        @SerializedName("USDTMT") var USDTMT : Double,
//        @SerializedName("USDTND") var USDTND : Double,
//        @SerializedName("USDTOP") var USDTOP : Double,
//        @SerializedName("USDTRY") var USDTRY : Double,
//        @SerializedName("USDTTD") var USDTTD : Double,
//        @SerializedName("USDTWD") var USDTWD : Double,
//        @SerializedName("USDTZS") var USDTZS : Double,
//        @SerializedName("USDUAH") var USDUAH : Double,
//        @SerializedName("USDUGX") var USDUGX : Double,
//        @SerializedName("USDUSD") var USDUSD : Int,
//        @SerializedName("USDUYU") var USDUYU : Double,
//        @SerializedName("USDUZS") var USDUZS : Double,
//        @SerializedName("USDVEF") var USDVEF : Double,
//        @SerializedName("USDVND") var USDVND : Int,
//        @SerializedName("USDVUV") var USDVUV : Double,
//        @SerializedName("USDWST") var USDWST : Double,
//        @SerializedName("USDXAF") var USDXAF : Double,
//        @SerializedName("USDXAG") var USDXAG : Double,
//        @SerializedName("USDXAU") var USDXAU : Double,
//        @SerializedName("USDXCD") var USDXCD : Double,
//        @SerializedName("USDXDR") var USDXDR : Double,
//        @SerializedName("USDXOF") var USDXOF : Double,
//        @SerializedName("USDXPF") var USDXPF : Double,
//        @SerializedName("USDYER") var USDYER : Double,
//        @SerializedName("USDZAR") var USDZAR : Double,
//        @SerializedName("USDZMK") var USDZMK : Double,
//        @SerializedName("USDZMW") var USDZMW : Double,
//        @SerializedName("USDZWL") var USDZWL : Double
        else -> null
    }
}