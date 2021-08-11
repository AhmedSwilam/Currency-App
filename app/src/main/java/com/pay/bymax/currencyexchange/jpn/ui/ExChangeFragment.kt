package com.pay.bymax.currencyexchange.jpn.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.pay.bymax.currencyexchange.jpn.R
import com.pay.bymax.currencyexchange.jpn.data.CurrencyAdapter
import com.pay.bymax.currencyexchange.jpn.data.models.Quotes
import com.pay.bymax.currencyexchange.jpn.databinding.FragmentExChangeBinding
import com.pay.bymax.currencyexchange.jpn.main.MainViewModel


class ExChangeFragment : Fragment() {

    lateinit var amount: String
    lateinit var binding: FragmentExChangeBinding
    private val viewModel: MainViewModel by viewModels()
    val currency = resources.getStringArray(R.array.currency)
    var quotesList: ArrayList<Quotes> = ArrayList()
    val currencyRecycler: CurrencyAdapter by lazy {
        CurrencyAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExChangeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recRanges.adapter = currencyRecycler
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, currency
            )
        }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context,
                    "$currency" + " " + "" + currency[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.button.setOnClickListener {
            viewModel.convert(
                binding.etAmount.text.toString(),
                binding.spinner.selectedItem.toString(),
                currencyRecycler.setAllCurrencyList(quotesList)
            )
        }

    }
}