package com.galaxy_techno.buyer.presentation.top_lvl_dest.home

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentAllProductCategoryBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.adapter.AllProductsCategoryAdapter
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate

class AllCategoryFragment :
    AuthFragment<FragmentAllProductCategoryBinding>(FragmentAllProductCategoryBinding::inflate),
    CountrySelectedDelegate {
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var countryAdapter: AllProductsCategoryAdapter
    private var mCountryList: MutableList<CountryVos> = mutableListOf()

    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.rvAllCategory)
    }

    override fun observe() {
        super.observe()

        viewModel.countryObj.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    setUpAllProductRecyclerView()
                    mCountryList = it.data!!.data as MutableList<CountryVos>
                    countryAdapter.setNewData(mCountryList)
                }
                else -> {
                    Toast.makeText(context, "fail ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpAllProductRecyclerView() {
        countryAdapter = AllProductsCategoryAdapter(requireContext(), this@AllCategoryFragment)
        recyclerView.apply {
            this.adapter = countryAdapter
            this.layoutManager = GridLayoutManager(context, 3)
            this.setHasFixedSize(true)
        }
    }

    override fun onClickSelectedCounty(data: CountryVos) {
        requireContext().displayToast(data.name)
        findNavController().popBackStack()
    }
}