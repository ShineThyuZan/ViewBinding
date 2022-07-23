package com.galaxy_techno.buyer.presentation.second_lvl_dest.profile

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentProfileShippingAddressBinding
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentProfileShippingAddress :
    SecondLvlFragment<FragmentProfileShippingAddressBinding>(FragmentProfileShippingAddressBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()
    var country: String? = null

    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "Shipping Addresses"
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.tilSelectCountry.setEndIconOnClickListener {
            findNavController().navigate(R.id.action_dest_profile_shipping_address_to_dest_country_select_bottom_sheet)
        }

        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observe() {
        super.observe()
        viewModel.countryName.observe(viewLifecycleOwner) {
            binding.etSelectCountry.setText(it)
        }
    }
}