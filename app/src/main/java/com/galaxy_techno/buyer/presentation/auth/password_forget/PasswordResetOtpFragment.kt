package com.galaxy_techno.buyer.presentation.auth.password_forget

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentPasswordResetOtpBinding
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordResetOtpFragment : AuthFragment<FragmentPasswordResetOtpBinding>(
    FragmentPasswordResetOtpBinding::inflate
) {

    override fun setUpListener() {
        super.setUpListener()
        binding.btnPasswordOtpVerify.setOnClickListener {
            findNavController().navigate(R.id.action_passwordResetOtpFragment_to_passwordResetConfirmFragment)
        }
    }
}