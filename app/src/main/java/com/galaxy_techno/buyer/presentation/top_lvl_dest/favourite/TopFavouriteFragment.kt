package com.galaxy_techno.buyer.presentation.top_lvl_dest.favourite

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentTopFavouriteBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.base.TopLvlFragment
import com.galaxy_techno.buyer.presentation.base.TopLvlToolbarFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.adapter.CountrySelectedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFavouriteFragment :
    TopLvlFragment<FragmentTopFavouriteBinding>(FragmentTopFavouriteBinding::inflate),FavItemClickedDelegate {
        private val favouriteViewModel : TopFavouriteViewModel by viewModels()
        private lateinit var favProductAdapter : TopFavProductAdapter
        private var list : MutableList<CountryVos> = mutableListOf()

    override fun initialize() {
        super.initialize()
        binding.toolbar.tvToolbarTitle.text = "My Favorites"
        favouriteViewModel.getCountryList()
    }

    override fun observe() {
        super.observe()
        favouriteViewModel.favProductObj.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    setUpCountryListRecyclerView()
                    list = it.data!!.data as MutableList<CountryVos>
                    favProductAdapter.setNewData(it.data!!.data as MutableList<CountryVos>)
                }

                else -> {
                    Toast.makeText(context, "fail ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpCountryListRecyclerView() {
        favProductAdapter = TopFavProductAdapter(requireContext(), this@TopFavouriteFragment)
        binding.rvFavProducts.apply {
            this.adapter = favProductAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClick(data: CountryVos) {
        requireContext().displayToast(data.name)
    }

    override fun onDeleteClicked(data: CountryVos) {
        requireContext().displayToast(data.name)
    }

    override fun onCartViewClicked(data: CountryVos) {
        requireContext().displayToast(data.name)
    }
}