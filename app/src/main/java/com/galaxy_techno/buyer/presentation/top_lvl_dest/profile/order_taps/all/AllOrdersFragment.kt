package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps.all
import android.app.Activity
import android.content.res.Configuration
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.databinding.FragmentOrderAllBinding

import com.galaxy_techno.buyer.presentation.base.ToolbarHiddenFragment
import com.galaxy_techno.buyer.ui.adapter.MovieLoadStateAdapter
import com.galaxy_techno.buyer.ui.adapter.MoviePagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class AllOrdersFragment : ToolbarHiddenFragment<FragmentOrderAllBinding>(FragmentOrderAllBinding::inflate) {

    private val movieAdapter: MoviePagingDataAdapter by lazy {
        MoviePagingDataAdapter(isDarkTheme(requireActivity())) { getItemClick(it) }.apply {
            this.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            binding.orderAllRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.orderAllRecycler.adapter = this
            binding.orderAllRecycler.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }
    private val viewModel: AllOrdersViewModel by viewModels()
    override fun setUpView() {
        super.setUpView()
        shouldShowShimmer(true)
    }
    private fun shouldShowShimmer(flag: Boolean) {
        if (flag) {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.orderAllRecycler.visibility = View.GONE
            binding.shimmerLayout.startShimmer()
        }else{
            binding.shimmerLayout.visibility = View.GONE
            binding.orderAllRecycler.visibility = View.VISIBLE
            binding.shimmerLayout.stopShimmer()
        }

    }

    override fun observe() {
        super.observe()
        viewModel.movies.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                delay(1000L)
                shouldShowShimmer(false)
                movieAdapter.submitData(it)
            }
        }
    }

    private fun getItemClick(position: Int) {
        val item = movieAdapter.getClickItem(position)
    }

    private fun isDarkTheme(activity: Activity): Boolean {
        return activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}