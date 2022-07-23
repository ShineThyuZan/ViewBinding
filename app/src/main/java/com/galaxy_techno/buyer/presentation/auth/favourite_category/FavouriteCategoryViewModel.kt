package com.galaxy_techno.buyer.presentation.auth.favourite_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.model.dto.CategoryItem
import com.galaxy_techno.buyer.model.dto.FavouriteCategoryDTO
import com.galaxy_techno.buyer.model.dto.toBody
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.InterestedCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * klt 12:30:21
 */

@HiltViewModel
class FavouriteCategoryViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _userId = MutableLiveData<Int>()
    private val userId: LiveData<Int> get() = _userId

    private val _categoryList = MutableStateFlow<UiState<FavouriteCategoryDTO>>(UiState.Empty())
    val categoryList get() = _categoryList.asStateFlow()

    private val _idList = mutableListOf<CategoryItem>()
    val idList: List<CategoryItem> get() = _idList

    private val _isFavouriteRegisterLoading = MutableLiveData<Boolean>()
    val isFavouriteRegisterLoading get() = _isFavouriteRegisterLoading

    private val _responseRegisterCategory =
        MutableSharedFlow<UiState<FavouriteCategoryDTO>>()
    val responseRegisterCategory get() = _responseRegisterCategory.asSharedFlow()

    init {
        getCategoryList()
        getUserId()
    }

    private fun getUserId() {
        viewModelScope.launch {
            userRepository.getUser().collect {

                _userId.value = it?.userId ?: 0
            }
        }
    }

    private fun getCategoryList() {
        viewModelScope.launch {

            _categoryList.emit(UiState.Loading())

            appRepository.getFavouriteList().collect {

                delay(2000L)
                _categoryList.emit(it)

                //todo : save to db : this will be user favourite.
                // check -> response 's status == "SUCCESS", then save data to DB
            }
        }
    }

    fun registerFavourite() {

        val interestedCategoryList = arrayListOf<InterestedCategory>()
        idList.forEach {
            interestedCategoryList.add(it.toBody())
        }

        val body = CategoryListRequest(
            userId.value ?: 0,
            interestedCategoryList
        )

        viewModelScope.launch {
            _responseRegisterCategory.emit(UiState.Loading())
            userRepository.registerFavouriteCategory(body).collect {
//                _responseRegisterCategory.value = it
                delay(2000L)
                if (it.data != null && it.data.status == Constant.STATUS_SUCCESS) {
                _responseRegisterCategory.emit(it)
                }
            }
        }
    }

    fun setCategoryItem(item: CategoryItem) {
        viewModelScope.launch {
            _idList.add(item)
        }

    }

    fun setFavouriteRegisterLoading(state: Boolean) {
        viewModelScope.launch {
            isFavouriteRegisterLoading.postValue(state)
        }
    }

    fun deleteCategoryItem(item: CategoryItem) {
        viewModelScope.launch {
            _idList.remove(item)
        }
    }

}