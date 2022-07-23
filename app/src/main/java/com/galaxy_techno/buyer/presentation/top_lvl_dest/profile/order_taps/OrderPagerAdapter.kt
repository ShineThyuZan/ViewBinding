package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps.all.AllOrdersFragment
class OrderPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllOrdersFragment()
            1 -> return PendingOrdersFragment()
            2 -> return ProcessingOrdersFragment()
            3 -> return DeliveredOrdersFragment()
            4 -> return RefundOrdersFragment()
            5 -> return CompletedOrdersFragment()
        }
        return AllOrdersFragment()
    }
}