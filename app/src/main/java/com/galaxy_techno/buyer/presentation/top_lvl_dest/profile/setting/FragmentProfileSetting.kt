package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.setting

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentProfileSettingBinding
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment

class FragmentProfileSetting :
    SecondLvlFragment<FragmentProfileSettingBinding>(FragmentProfileSettingBinding::inflate) {
    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "My Setting"
        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnLogoutInSetting.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_auth)

        }
    }
}