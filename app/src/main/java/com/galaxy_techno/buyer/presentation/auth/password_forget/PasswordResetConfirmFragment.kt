package com.galaxy_techno.buyer.presentation.auth.password_forget

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentPasswordResetConfirmBinding
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordResetConfirmFragment : AuthFragment<FragmentPasswordResetConfirmBinding>(
    FragmentPasswordResetConfirmBinding::inflate
) {

    override fun setUpListener() {
        super.setUpListener()
        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_passwordResetConfirmFragment_to_passwordResetSuccess)
        }
    }
}