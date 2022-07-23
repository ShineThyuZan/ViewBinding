package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.databinding.ViewDropDownBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
class CustomDropDownAdapter(context: Context, private val countryList: List<CountryVos>) :
    ArrayAdapter<CountryVos>(context, 0, countryList) {

    private lateinit var view: View
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = ViewDropDownBinding.inflate(LayoutInflater.from(context), parent, false)
        val country = countryList[position]
        binding.txtDropDownLabel.text = country.name

        Glide.with(context)
            .load(country.photo)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.ivCountry)
        return binding.root

    }

}