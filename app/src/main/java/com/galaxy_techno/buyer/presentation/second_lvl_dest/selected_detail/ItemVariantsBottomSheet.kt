package com.galaxy_techno.buyer.presentation.second_lvl_dest.selected_detail

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.BottomSheetVariantsBinding
import com.galaxy_techno.buyer.presentation.base.BaseBottomSheet



class ItemVariantsBottomSheet : BaseBottomSheet<BottomSheetVariantsBinding>(
    BottomSheetVariantsBinding::inflate
) {
    override fun initialize() {
        super.initialize()
        dialog?.setCanceledOnTouchOutside(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupListener() {
        super.setupListener()

        binding.btnCheckoutVariant.setOnClickListener {
            findNavController().navigate(R.id.action_dest_variant_to_checkout_navigation)
        }

        binding.ivCloseVariant.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.ivCloseVariant.setImageResource(R.drawable.close_icon_grey_900)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    findNavController().popBackStack()
                    false
                }
                else -> {
                    false
                }
            }

        }
    }

    override fun setupView() {
        super.setupView()
        dialog?.window!!.setWindowAnimations(R.style.AnimationForBottomSheet)
    }

}