package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.FragmentTopProfileBinding
import com.galaxy_techno.buyer.model.entity.User
import com.galaxy_techno.buyer.presentation.base.TopLvlFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopProfileFragment :
    TopLvlFragment<FragmentTopProfileBinding>(FragmentTopProfileBinding::inflate) {
    private val profileViewModel: TopProfileViewModel by viewModels()
    private var isLoggedIn: Boolean = false

    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "My Profile"
    }

    override fun setUpListener() {
        super.setUpListener()


        binding.orderItem.setOnClickListener {
            findNavController().navigate(R.id.action_topProfileFragment_to_fragmentProfileMyOrder)
            requireContext().displayToast("order")
        }
        binding.shippingItem.setOnClickListener {
            findNavController().navigate(R.id.action_topProfileFragment_to_fragmentProfileShippingAddress)
            requireContext().displayToast("Address")

        }
        binding.paymentItem.setOnClickListener {
            findNavController().navigate(R.id.action_topProfileFragment_to_fragmentProfilePaymentMethod)
            requireContext().displayToast("Payment Method")
        }
        binding.reviewItem.setOnClickListener {
            findNavController().navigate(R.id.action_topProfileFragment_to_fragmentProfileMyReview)
            requireContext().displayToast("Review")
        }
        binding.settingItem.setOnClickListener {
            findNavController().navigate(R.id.action_topProfileFragment_to_fragmentProfileSetting)
            requireContext().displayToast("Setting")
        }
    }

    override fun observe() {
        super.observe()
        profileViewModel.isLoggedIn.observe(viewLifecycleOwner) {
            isLoggedIn = it
        }
        profileViewModel.user.observe(viewLifecycleOwner) {
            showUser(it)
        }
        profileViewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->

            if (userInfo!!.success) {
                binding.loadingInProfile.visibility = View.GONE
                binding.tvUserName.text = userInfo.data.userName
                Glide.with(requireContext())
                    .load(userInfo.data.photo)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivProfile)

                binding.tvPhoneNo.text = userInfo.data.userID
            } else {
                binding.loadingInProfile.visibility = View.VISIBLE
            }
        }

    }

    private fun showUser(user: User?) {
        //    binding.tvUser.text = user.toString()
    }
}