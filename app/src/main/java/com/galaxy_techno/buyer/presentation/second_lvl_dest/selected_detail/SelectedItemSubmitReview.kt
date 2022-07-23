package com.galaxy_techno.buyer.presentation.second_lvl_dest.selected_detail

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.SubmitProductReviewBinding
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import timber.log.Timber

class SelectedItemSubmitReview : SecondLvlFragment<SubmitProductReviewBinding> (
    SubmitProductReviewBinding::inflate
        ) {
    override fun initialize() {
        super.initialize()
        binding.rbSubmitProductReview.rating = 5f
    }

    override fun setUpListener() {
        super.setUpListener()

        Timber.tag("Listener Test").d("Listener Work")
        binding.clSubmitReview.setOnFocusChangeListener{ v, hasFocus ->
            if(v == binding.clSubmitReview && hasFocus) {
                hideKeyBoard()
            }
        }

        binding.btnSubmitReview.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_to_all_review)
        }
    }

    override fun setUpView() {
        super.setUpView()
    }
}