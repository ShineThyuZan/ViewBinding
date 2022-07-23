package com.galaxy_techno.buyer.presentation.second_lvl_dest.selected_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.CountryDTO
import com.galaxy_techno.buyer.model.dto.CountryListResponse
import com.galaxy_techno.buyer.model.dto.SelectedProductDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedItemViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(){

    private val _selectedProduct = MutableLiveData<UiState<SelectedProductDTO>>()
    val selectedProduct : MutableLiveData<UiState<SelectedProductDTO>> get() = _selectedProduct

    private val _selectedCountry = MutableLiveData<UiState<CountryListResponse>>()
    val selectedCountry : MutableLiveData<UiState<CountryListResponse>> get() = _selectedCountry

    fun getSelectedProduct() {
        viewModelScope.launch {
            _selectedProduct.value = (UiState.Loading())
            appRepository.getSelectedProduct().collect {
                _selectedProduct.postValue(it)
            }
        }
    }

    fun getSelectedCountry() {
        viewModelScope.launch {
            _selectedCountry.value = (UiState.Loading())
            appRepository.getCountryList().collect {
                _selectedCountry.postValue(it)
            }
        }
    }
}