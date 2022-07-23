package com.galaxy_techno.buyer.presentation.second_lvl_dest.checkout

import com.galaxy_techno.buyer.databinding.FragmentProfileShippingAddressBinding
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import timber.log.Timber

class FragmentAddNewAddress : SecondLvlFragment<FragmentProfileShippingAddressBinding>(
    FragmentProfileShippingAddressBinding::inflate
) {

    override fun setUpListener() {
        super.setUpListener()
        binding.tilSelectCountry.setEndIconOnClickListener {
            Timber.tag("Clickable").d("Clicking")
        }
    }

}


