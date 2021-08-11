package com.pay.bymax.currencyexchange.jpn.data.models

import com.google.gson.annotations.SerializedName

   
data class CurrencyResponse (

   @SerializedName("success") var success : Boolean,
   @SerializedName("terms") var terms : String,
   @SerializedName("privacy") var privacy : String,
   @SerializedName("timestamp") var timestamp : Int,
   @SerializedName("source") var source : String,
   @SerializedName("quotes") var quotes : Quotes

)