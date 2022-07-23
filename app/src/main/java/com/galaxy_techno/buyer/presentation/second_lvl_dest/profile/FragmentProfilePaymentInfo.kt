package com.galaxy_techno.buyer.presentation.second_lvl_dest.profile

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.databinding.FragmentProfilePaymentInfoBinding
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment

class FragmentProfilePaymentInfo :
    SecondLvlFragment<FragmentProfilePaymentInfoBinding>(FragmentProfilePaymentInfoBinding::inflate) {
    override fun setUpListener() {
        super.setUpListener()
        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "Add Payment"
    }
}