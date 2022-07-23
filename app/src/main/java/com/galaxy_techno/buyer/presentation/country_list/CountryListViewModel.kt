package com.galaxy_techno.buyer.presentation.country_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val appRepository: AppRepository

) : ViewModel() {

    private val _countryObj = MutableLiveData<UiState<CountryListResponse>>()
    val countryObj : LiveData<UiState<CountryListResponse>> get() = _countryObj
    private fun getCountryList() {
        viewModelScope.launch {
            appRepository.getCountryList().collect {
                _countryObj.value = it
            }
        }
    }
    // stateList
    private val _stateList = MutableLiveData<UiState<StateListDTO>>()
    val stateList : LiveData<UiState<StateListDTO>> get() = _stateList

    private val _idStateList = mutableListOf<State>()
    val idList: List<State> get() = _idStateList

    private val _searchResult = MutableLiveData<UiState<SearchProductResponse>>()
    val searchResult : LiveData<UiState<SearchProductResponse>> get() = _searchResult

    // township list
    private val _townshipList = MutableLiveData<UiState<TownshipDTO>>()
    val townshipList : LiveData<UiState<TownshipDTO>> get() = _townshipList

    private val _idTownshipList = mutableListOf<TownshipData>()
    val idTownshipList: List<TownshipData> get() = _idTownshipList

    init {
        getCountryList()
        getTownshipList()
    }



    fun getStateList() {
        viewModelScope.launch {
            _stateList.value = (UiState.Loading())
            appRepository.getStateList().collect {
                _stateList.postValue(it)
            }
        }
    }
    fun searchResult(userId : String, pageNo : Int , count : Int , keyword : String){
        viewModelScope.launch {

            appRepository.getSearchUser(userId, pageNo,count,keyword).collect {

                _searchResult.postValue(it)
            }
        }
    }

    fun setStateItem(item: State) {
        viewModelScope.launch {
            _idStateList.add(item)
        }

    }

    fun deleteStateItem(item: State) {
        viewModelScope.launch {
            _idStateList.remove(item)
        }
    }

    private fun getTownshipList() {
        viewModelScope.launch {
            _townshipList.postValue(UiState.Loading())
            appRepository.getTownshipList().collect {

                _townshipList.postValue(it)
                //todo : save to db : this will be user favourite.
                // check -> response 's status == "SUCCESS", then save data to DB
            }
        }
    }

    fun setStateItem(item: TownshipData) {
        viewModelScope.launch {
            _idTownshipList.add(item)
        }

    }

    fun deleteStateItem(item: TownshipData) {
        viewModelScope.launch {
            _idTownshipList.remove(item)
        }
    }


}