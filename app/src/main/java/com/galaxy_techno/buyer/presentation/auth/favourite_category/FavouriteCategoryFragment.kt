package com.galaxy_techno.buyer.presentation.auth.favourite_category

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentFavouriteCategoryBinding
import com.galaxy_techno.buyer.databinding.LayoutSingleChipBinding

import com.galaxy_techno.buyer.model.dto.CategoryList
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.presentation.extension.displaySnack
import com.galaxy_techno.buyer.presentation.extension.isVerifiedChip
import com.galaxy_techno.buyer.presentation.extension.setActive
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FavouriteCategoryFragment : AuthFragment<FragmentFavouriteCategoryBinding>(
    FragmentFavouriteCategoryBinding::inflate
) {

    private val viewModel: FavouriteCategoryViewModel by viewModels()
    private var _chipBinding: LayoutSingleChipBinding? = null
    private val chipBinding get() = _chipBinding!!

    override fun initialize() {
        super.initialize()
        _chipBinding =
            LayoutSingleChipBinding.inflate(layoutInflater, binding.cgFavouriteCategory, false)

    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.categoryList.collect {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {
                        //just show progress bar
                    }
                    is UiState.Success -> {
                        if (it.data!!.status == Constant.STATUS_SUCCESS) {
                            showChips(true)
                            setupChips(it.data.data)
                        } else {
                            //show error
                            Timber.tag(it.data.error)
                        }
                    }
                    else -> {

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.responseRegisterCategory.collectLatest {
                when (it) {
                    is UiState.Empty -> {
                        //Unit
                    }
                    is UiState.Error -> {
                        viewModel.setFavouriteRegisterLoading(false)
                        binding.root.displaySnack(getString(R.string.snack_request_register_fail))
                        //show error
                    }
                    is UiState.Loading -> {
                        //just show progress bar
                        viewModel.setFavouriteRegisterLoading(true)
                    }
                    is UiState.Success -> {
                        if (it.data!!.status == Constant.STATUS_SUCCESS) {
                            viewModel.setFavouriteRegisterLoading(false)
                            delay(500L)
                            binding.btnFavouriteContinue.setActive(false)
                            binding.btnSkip.setActive(false)

                            //hide progress dialog
                            navigateToHome()
                            // set popUp and Home Frag to start.

                        } else {
                            //show error
                            Timber.tag(it.data.error)
                        }
                    }
                    else -> {

                    }
                }
            }
        }

        viewModel.isFavouriteRegisterLoading.observe(viewLifecycleOwner) {
            if (it) {
                (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_loading))
            } else {
                (activity as MainActivity).hideLoadingDialog()
            }
        }
    }

    private fun showChips(isOk: Boolean) {
        if (isOk) {
            binding.progress.visibility = View.GONE
            binding.cgFavouriteCategory.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.VISIBLE
            binding.cgFavouriteCategory.visibility = View.GONE
        }
    }

    private fun setupChips(category: CategoryList?) {

        category?.categoryList?.forEach {

            val chip = Chip(requireContext())
            chip.apply {
                this.id = it.categoryId
                this.text = it.categoryName
                this.isCheckable = true
//                this.isCheckedIconVisible = true
//                this.checkedIconTint =
//                    ContextCompat.getColorStateList(requireContext(), R.color.chip_text_color)
                this.checkedIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_circle)
                this.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) viewModel.setCategoryItem(it)
                    else viewModel.deleteCategoryItem(it)
                    Timber.d("${buttonView.text} : $isChecked")
                }
                binding.cgFavouriteCategory.addView(this)
            }

            //todo : root parent view has to be deleted first
            /* chipBinding.root.apply {
                 this.id = it.categoryId
                 this.text = it.categoryName
                 this.setOnCheckedChangeListener { buttonView, isChecked ->
                     Timber.tag("${buttonView.text} : $isChecked")
                 }
                 binding.chipGroup.addView(this)
             }*/
        }

    }

    override fun setUpListener() {
        super.setUpListener()

        binding.btnFavouriteContinue.setOnClickListener {
            if (validate()) {
                viewModel.registerFavourite()
            } else {
                binding.root.displaySnack(getString(R.string.snack_request_chip_select))
            }
        }

        binding.btnSkip.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        val favNavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.dest_top_home, true)
            .build()

        findNavController().navigate(
            R.id.action_category_to_home,
            Bundle(),
            favNavOptions
        )
    }

    private fun validate(): Boolean {
        if (!binding.cgFavouriteCategory.isVerifiedChip()) {
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _chipBinding = null
    }

}