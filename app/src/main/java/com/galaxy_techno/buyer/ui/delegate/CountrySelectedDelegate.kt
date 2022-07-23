package com.galaxy_techno.buyer.ui.delegate

import com.galaxy_techno.buyer.model.dto.CountryVos

interface CountrySelectedDelegate {
    fun onClickSelectedCounty(data : CountryVos)
}