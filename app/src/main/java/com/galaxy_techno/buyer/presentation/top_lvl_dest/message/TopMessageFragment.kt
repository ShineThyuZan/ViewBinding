package com.galaxy_techno.buyer.presentation.top_lvl_dest.message

import com.galaxy_techno.buyer.databinding.FragmentTopMessageBinding
import com.galaxy_techno.buyer.presentation.base.TopLvlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopMessageFragment :
    TopLvlFragment<FragmentTopMessageBinding>(FragmentTopMessageBinding::inflate) {
    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "Message"
    }
}