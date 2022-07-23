package com.galaxy_techno.buyer.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.di.Qualifier
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.model.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository

) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    init {
        getUserFromDb()
    }
    private fun getUserFromDb() {
        viewModelScope.launch {

            userRepository.getUser().collect {
                Timber.tag("userdata").d(it.toString())
//                it?.let {
//                    _user.value = it
//                } ?: run {
//                    _user.value = null
//                }
                _user.value = it
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser()
            appRepository.storeAuthState(false)
            getUserFromDb()
        }

    }
}