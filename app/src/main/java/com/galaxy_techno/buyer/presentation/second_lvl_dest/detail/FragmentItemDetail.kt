package com.galaxy_techno.buyer.presentation.second_lvl_dest.detail

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentItemDetailBinding

import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.ui.adapter.DetailProductAdapter
import com.galaxy_techno.buyer.ui.delegate.DetailProductDelegate
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentItemDetail :
    SecondLvlFragment<FragmentItemDetailBinding>(FragmentItemDetailBinding::inflate) {
private val fragmentItemDetailViewModel: FragmentItemDetailViewModel by activityViewModels()

    override fun setUpListener() {
        super.setUpListener()
    }

    override fun setUpView() {
        super.setUpView()
//        setUpPager()
        binding.rbItemDetail.rating = 3.1f
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

//     private fun setUpPager() {
//        val groupAdapter =
//            FragmentItemDetailAdapter(childFragmentManager, lifecycle)
//        binding.vpItemDetail.apply {
//            this.adapter = groupAdapter
//            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            this.registerOnPageChangeCallback(callback)
//
//            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
//
//            TabLayoutMediator(binding.tlItemDetail, binding.vpItemDetail) { tab, position ->
//                when (position) {
//                    0 -> {
///*do something*/Timber.tag("show photo no.1")
//                    }
//                    1 -> {
///*do something*/Timber.tag("show photo no.2")
//                    }
//                    2 -> {
///*do something*/Timber.tag("show photo no.3")
//                    }
//
//                }
//            }.attach()
//        }
//    }


    override fun onDestroyView() {
//        binding.vpItemDetail.unregisterOnPageChangeCallback(callback)
        super.onDestroyView()
    }

}