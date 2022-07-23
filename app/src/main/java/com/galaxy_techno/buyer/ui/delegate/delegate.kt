package com.galaxy_techno.buyer.ui.delegate

import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.State

interface delegate {
    fun onProductOnClick(app: State)
}