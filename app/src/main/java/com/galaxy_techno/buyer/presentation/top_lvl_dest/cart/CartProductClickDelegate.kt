package com.galaxy_techno.buyer.presentation.top_lvl_dest.cart

import com.galaxy_techno.buyer.model.dto.CountryVos

interface CartProductClickDelegate {
    fun onClickCartView(data : CountryVos)
    fun onClickAddButton(data: CountryVos, count : Int )
    fun onClickMinusButton(data : CountryVos, count : Int )
    fun onClickMore(data: CountryVos)
}
