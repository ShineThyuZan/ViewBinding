package com.galaxy_techno.buyer.presentation.second_lvl_dest.checkout

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.CheckoutInfoRequestBinding

import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment

class FragmentCheckoutInfoRequest : SecondLvlFragment<CheckoutInfoRequestBinding>(
    CheckoutInfoRequestBinding::inflate
) {
    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "Checkout"
    }

    override fun setUpListener() {
        super.setUpListener()

        binding.clRequestAddress.setOnClickListener {
            findNavController().navigate(R.id.action_dest_checkout_info_request_to_dest_profile_shipping_address)
        }

        binding.clRequestPaymentInfo.setOnClickListener {
            findNavController().navigate(R.id.action_dest_checkout_info_request_to_dest_profile_payment_info)
        }

        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}