package com.galaxy_techno.buyer.presentation.second_lvl_dest.search

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.ProductFilterBottomSheetBinding
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.presentation.base.BaseBottomSheet
import com.galaxy_techno.buyer.presentation.product.ProductViewModel
import com.galaxy_techno.buyer.ui.adapter.FilterAdapter
import com.galaxy_techno.buyer.ui.delegate.CategoryNameDelegate
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductsFilterBottomNavigationFragment :
    BaseBottomSheet<ProductFilterBottomSheetBinding>(ProductFilterBottomSheetBinding::inflate),
    CategoryNameDelegate, DiscountProductDelegate {
    private val productViewModel: ProductViewModel by viewModels()
    private var mainCategoryAdapter: FilterAdapter? = null
    private val args: ProductsFilterBottomNavigationFragmentArgs by navArgs()
    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.rvFilterCategory)
    }

    override fun initialize() {
        super.initialize()
        dialog?.setCanceledOnTouchOutside(false)
        productViewModel.getMainCategoryItems()
    }

    override fun setupView() {
        super.setupView()
        Toast.makeText(context, args.productId, Toast.LENGTH_SHORT).show()
    }

    override fun setupListener() {
        super.setupListener()
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnFilter.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            productViewModel.productList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {

                        binding.rvFilterCategory.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        if (it.data!!.success) {
                            //           binding.swipeRefresh.isRefreshing = false
                            Timber.tag("state").d(it.data.data.size.toString())
                            binding.rvFilterCategory.visibility = View.VISIBLE
                            /** setupRecyclerView */
                            setupRecyclerView()
                            mainCategoryAdapter?.setNewData(it.data.data as MutableList<ItemDTO.App>)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        mainCategoryAdapter =
            FilterAdapter(requireContext(), this@ProductsFilterBottomNavigationFragment, this)
        binding.rvFilterCategory.apply {
            this.adapter = mainCategoryAdapter
            this.layoutManager = FlexboxLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClickedName(categoryName: ItemDTO.App) {
        Toast.makeText(context, categoryName.category, Toast.LENGTH_SHORT).show()
    }

    override fun onProductOnClick(app: Apps) {
        Toast.makeText(context, app.name, Toast.LENGTH_SHORT).show()
    }
}
