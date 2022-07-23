package com.galaxy_techno.buyer.presentation.single_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState

    init {
        getAuthState()
    }

    private fun getAuthState() {
        viewModelScope.launch {
            appRepository.getAuthState().collect {
                _authState.postValue(it)
            }
        }
    }

}