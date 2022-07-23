package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps

import com.galaxy_techno.buyer.databinding.FragmentOrderCompletedBinding
import com.galaxy_techno.buyer.presentation.base.ToolbarHiddenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedOrdersFragment :
    ToolbarHiddenFragment<FragmentOrderCompletedBinding>(FragmentOrderCompletedBinding::inflate)