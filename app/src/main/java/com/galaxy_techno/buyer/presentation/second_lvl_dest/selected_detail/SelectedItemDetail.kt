package com.galaxy_techno.buyer.presentation.second_lvl_dest.selected_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentItemSelectedDetailBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.model.dto.ProductData
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.adapter.ProductVariantAdapter
import com.galaxy_techno.buyer.ui.adapter.SelectedProductAdapter
import com.galaxy_techno.buyer.ui.delegate.SelectedProductDelegate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SelectedItemDetail : SecondLvlFragment<FragmentItemSelectedDetailBinding>(
    FragmentItemSelectedDetailBinding::inflate), SelectedProductDelegate {
    private val selectedItemViewModel: SelectedItemViewModel by viewModels()
    private var selectedProductAdapter: SelectedProductAdapter? = null
    private var productVariantAdapter: ProductVariantAdapter? = null

    override fun initialize() {
        super.initialize()
     //   selectedItemViewModel.getSelectedProduct()
        selectedItemViewModel.getSelectedCountry()
        binding.ratingBar.rating = 3.25f
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.tbSelectedItem.ivBack.setOnClickListener {
           // findNavController().navigate(R.id.action_dest_selected_item_detail_to_dest_item_detail_list)
            findNavController().popBackStack()
        }
        binding.tbSelectedItem.clSearchHolder.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_to_search)
        }

        binding.tbSelectedItem.ivMic.setOnClickListener {
            requireContext().displayToast("Search with Voice")
        }
        binding.tbSelectedItem.ivCamera.setOnClickListener {
            requireContext().displayToast("Search with Photo")
        }
        binding.tbSelectedItem.ivAddToCart.setOnClickListener {
            requireContext().displayToast("Add to Cart")
        }
        binding.tvVariantMore.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_to_variant)
        }
        binding.tvAllReview.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_to_all_review)
        }
        binding.btnReview.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_to_summit)
        }
        binding.btnSelectedCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_dest_selected_detail_to_checkout_navigation)
        }

    }

    override fun setUpView() {
        super.setUpView()
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            selectedItemViewModel.selectedCountry.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {

                    }
                    is UiState.Error -> {

                    }
                    is UiState.Loading -> {
                        binding.clSelectedProduct.visibility = View.INVISIBLE
                        binding.progress.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progress.visibility = View.GONE
                        binding.clSelectedProduct.visibility = View.VISIBLE
                        setUpRecyclerView()

                        selectedProductAdapter?.setNewData(it.data!!.data as MutableList<CountryVos>)

                        productVariantAdapter?.setNewData(it.data!!.data as MutableList<CountryVos>)
                    } else -> {

                }                    }
            }
        }
    }

    private fun setUpRecyclerView() {
        selectedProductAdapter = SelectedProductAdapter(requireContext(), this@SelectedItemDetail)
        productVariantAdapter = ProductVariantAdapter(requireContext(), this@SelectedItemDetail)

        binding.rvVariantImg.apply {
            this.adapter = productVariantAdapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        }
        binding.rvSelectedProduct.apply {
            this.adapter = selectedProductAdapter
            this.layoutManager = GridLayoutManager(context, 2,)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClickVariants() {
        Timber.tag("Click").d("Click Event")
//        findNavController().navigate(R.id.action_dest_selected_item_detail_self)
    }

}