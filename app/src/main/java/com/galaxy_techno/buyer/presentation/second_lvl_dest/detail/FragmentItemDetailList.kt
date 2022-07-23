package com.galaxy_techno.buyer.presentation.second_lvl_dest.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentItemDetailListBinding
import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.product.ProductViewModel
import com.galaxy_techno.buyer.ui.adapter.DetailProductAdapter
import com.galaxy_techno.buyer.ui.delegate.DetailProductDelegate
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentItemDetailList :
    SecondLvlFragment<FragmentItemDetailListBinding>(FragmentItemDetailListBinding::inflate),
    DetailProductDelegate {
    private val fragmentItemDetailViewModel: ProductViewModel by viewModels()
    private var detailProductAdapter: DetailProductAdapter? = null

    override fun initialize() {
        super.initialize()
        fragmentItemDetailViewModel.getMainCategoryItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_detail_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productDetailToolbar.inflateMenu(R.menu.product_detail_menu)
    }

    override fun setUpView() {
        super.setUpView()
        binding.productDetailToolbar.setNavigationIcon(R.drawable.back_arrow_white)
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.productDetailToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    findNavController().navigate(R.id.action_dest_detail_list_to_search)
                }
                R.id.action_add_to_cart -> {
                    /**do something*/
                }
            }
            true
        }

        binding.productDetailToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            fragmentItemDetailViewModel.productList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {
                        //just show progress bar
                        binding.clProductDetail.visibility = View.INVISIBLE
                        binding.progress.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progress.visibility = View.GONE
                        binding.clProductDetail.visibility = View.VISIBLE
//                            binding.progress.visibility = View.GONE
                        /** setupRecyclerView */
                        setupRecyclerView()
                        Timber.tag("api").d("api${it.data!!.data.size}")
                        detailProductAdapter?.setNewData(it.data.data as MutableList<ItemDTO.App>)


                    }
                    else -> {
                    }
                }
            }
        }

    }

    private fun setupRecyclerView() {
        detailProductAdapter = DetailProductAdapter(requireContext(), this@FragmentItemDetailList)
        binding.rvProductDetail.apply {
            this.adapter = detailProductAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    private var callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }

    override fun onClickProduct(app: ItemDTO.App) {
        findNavController().navigate(R.id.action_dest_list_to_selected)
    }

    override fun onClickCheckout(app: ItemDTO.App) {
        findNavController().navigate(R.id.action_dest_detail_list_to_checkout_navigation)
    }
}