package com.galaxy_techno.buyer.presentation.home

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentHomeBinding
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : AuthFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun observe() {
        super.observe()
        homeViewModel.user.observe(viewLifecycleOwner) {
//            binding.tvName.text = it.name
            binding.tvName.apply {
                text = it?.name ?: run {
                    "this is null"
                }
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.btnCountry.setOnClickListener {
//            findNavController().navigate(R.id.action_home_to_country)
        }

        binding.btnLogout.setOnClickListener {
            homeViewModel.deleteUser()
            Toast.makeText(context, "logout ", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogoutInProfile .setOnClickListener {
//            findNavController().navigate(R.id.action_home_to_login)
        }
    }


}