package com.galaxy_techno.buyer.presentation.auth.password_forget

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentPasswordResetSuccessBinding
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordResetSuccessFragment : AuthFragment<FragmentPasswordResetSuccessBinding>(
    FragmentPasswordResetSuccessBinding::inflate
) {

    override fun setUpListener() {
        super.setUpListener()
        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_passwordResetSuccess_to_loginFragment)
        }
    }
}