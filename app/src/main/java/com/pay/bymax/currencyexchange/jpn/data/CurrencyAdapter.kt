package com.pay.bymax.currencyexchange.jpn.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pay.bymax.currencyexchange.jpn.R
import com.pay.bymax.currencyexchange.jpn.data.models.Quotes

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private var currencyList: ArrayList<Quotes> = ArrayList()

    fun setAllCurrencyList(currenciesList: ArrayList<Quotes>) {
        this.currencyList = currenciesList
        notifyDataSetChanged()
    }

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.txt_name)
        var amount: TextView = itemView.findViewById(R.id.txt_amount)

        fun bind(quote: Quotes) {
            name.text = listOf(quote).toString()
            amount.text = listOf(quote).toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_list, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val quote: Quotes = currencyList[position]
        holder.bind(quote)
    }

    // بجيب العدد بتاعهم من الاراي ليست اللى فوق
    override fun getItemCount(): Int {
        return currencyList.size
    }
}