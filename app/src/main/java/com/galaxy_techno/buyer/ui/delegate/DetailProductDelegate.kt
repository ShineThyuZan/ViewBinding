package com.galaxy_techno.buyer.ui.delegate

import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.DetailItemDTO
import com.galaxy_techno.buyer.model.dto.Info
import com.galaxy_techno.buyer.model.dto.ItemDTO

interface DetailProductDelegate {
    fun onClickProduct(app: ItemDTO.App)
    fun onClickCheckout(app: ItemDTO.App)
}