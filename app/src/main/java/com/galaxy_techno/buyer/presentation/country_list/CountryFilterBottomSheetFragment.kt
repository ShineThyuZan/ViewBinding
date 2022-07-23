package com.galaxy_techno.buyer.presentation.country_list

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.CountryFilterBottomSheetBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.base.BaseBottomSheet
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.adapter.CountrySelectedAdapter
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFilterBottomSheetFragment :
    BaseBottomSheet<CountryFilterBottomSheetBinding>(CountryFilterBottomSheetBinding::inflate),
    CountrySelectedDelegate {

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var countryAdapter: CountrySelectedAdapter
    private var mCountryList: MutableList<CountryVos> = mutableListOf()


    override fun setupView() {
        super.setupView()
        dialog?.setCanceledOnTouchOutside(true)
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observe() {
        super.observe()

        viewModel.countryObj.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    setUpCountryListRecyclerView()
                    mCountryList = it.data!!.data as MutableList<CountryVos>
                    countryAdapter.setNewData(mCountryList)
                }
                else -> {
                    Toast.makeText(context, "fail ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpCountryListRecyclerView() {
        countryAdapter = CountrySelectedAdapter(requireContext(), this@CountryFilterBottomSheetFragment)
        binding.rvCountry.apply {
            this.adapter = countryAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClickSelectedCounty(data: CountryVos) {
        requireContext().displayToast(data.name)
        viewModel.setCountryCode(data.id)
        viewModel.setCountryName(data.name)
        findNavController().popBackStack()
    }


}