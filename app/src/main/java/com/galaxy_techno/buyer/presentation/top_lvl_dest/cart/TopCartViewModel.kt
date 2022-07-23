package com.galaxy_techno.buyer.presentation.top_lvl_dest.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.model.dto.CountryListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopCartViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(){

    /** need to change fav Product item model and api */
    private val _cartProductObj = MutableLiveData<UiState<CountryListResponse>>()
    val cartProductObj : LiveData<UiState<CountryListResponse>> get() = _cartProductObj

    /** to change fun name */
    fun getCountryList() {
        viewModelScope.launch {
            appRepository.getCountryList().collect {
                _cartProductObj.value = it
            }
        }
    }
}