package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps

import com.galaxy_techno.buyer.databinding.FragmentOrderDeliveredBinding
import com.galaxy_techno.buyer.presentation.base.BaseFragment
import com.galaxy_techno.buyer.presentation.base.ToolbarHiddenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveredOrdersFragment :
    ToolbarHiddenFragment<FragmentOrderDeliveredBinding>(FragmentOrderDeliveredBinding::inflate)