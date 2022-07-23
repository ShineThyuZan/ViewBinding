package com.galaxy_techno.buyer.presentation.top_lvl_dest.favourite

import com.galaxy_techno.buyer.model.dto.CountryVos

interface FavItemClickedDelegate {
    fun onClick(data : CountryVos)
    fun onDeleteClicked(data : CountryVos)
    fun onCartViewClicked(data: CountryVos)
}