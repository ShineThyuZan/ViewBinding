package com.galaxy_techno.buyer.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.model.dto.StateListDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(){

    // stateList
    private val _productList = MutableLiveData<UiState<ItemDTO>>()
    val productList : LiveData<UiState<ItemDTO>> get() = _productList

    fun getMainCategoryItems() {
        viewModelScope.launch {
            _productList.value = (UiState.Loading())
            appRepository.getMainCategoryItem().collect {
                _productList.postValue(it)
            }
        }
    }




}