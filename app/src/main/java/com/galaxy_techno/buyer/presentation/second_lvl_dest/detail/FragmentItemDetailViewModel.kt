package com.galaxy_techno.buyer.presentation.second_lvl_dest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.DetailItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentItemDetailViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _productDetailList = MutableLiveData<UiState<DetailItemDTO>>()
    val productDetailList: LiveData<UiState<DetailItemDTO>> get() = _productDetailList

//    fun getDetailProduct() {
//        viewModelScope.launch {
//            _productDetailList.value = UiState.Loading()
//            appRepository.getDetailCategoryItem().collect {
//                _productDetailList.postValue(it)
//            }
//        }
//    }
}