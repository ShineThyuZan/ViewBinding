package com.galaxy_techno.buyer.presentation.top_lvl_dest.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.CountryListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopFavouriteViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(){

    /** need to change fav Product item model and api */
    private val _favProductObj = MutableLiveData<UiState<CountryListResponse>>()
    val favProductObj : LiveData<UiState<CountryListResponse>> get() = _favProductObj

    /** to change fun name */
    fun getCountryList() {
        viewModelScope.launch {
            appRepository.getCountryList().collect {
                _favProductObj.value = it
            }
        }
    }
}