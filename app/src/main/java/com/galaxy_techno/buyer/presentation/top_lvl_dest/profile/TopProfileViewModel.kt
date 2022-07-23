package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.model.dto.UserProfileResponse
import com.galaxy_techno.buyer.model.entity.User
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _userProfileInfo = MutableLiveData<UserProfileResponse?>()
    val userInfo: LiveData<UserProfileResponse?> get() = _userProfileInfo

    private fun isAdyLoggedIn() {
        viewModelScope.launch {
            appRepository.getAuthState().collect {
                _isLoggedIn.value = it
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().collect {


                _user.value = it ?: null
            }
        }
    }

    fun getUserInfo(authorization: String, request: UserProfileRequest) {
        viewModelScope.launch {
            userRepository.userProfileInfo(authorization, request).collect {
                _userProfileInfo.postValue(it.data)
            }
        }
    }

    init {
        isAdyLoggedIn()
        getUser()
        getUserInfo("Bearer fVtOjJ0CCJH01UaDo8dvS_BgErrX2fKlS7d2HpZgRxmaqyp92JqyfuLLwQH8GLkiVGsyZkFuD4qfaHIcq7e_TBfJ630VklWkPpENL_kdM-8HOlHzpPa3d70NmZskgvyrzL0XAudMV4KkxKAP3McbwS_gLZ44_1JuEvNemJ0ueN1QzFaXER5JIfC_DpECh2nRNqv9QgeBK-eR0rtpERc7AGm5zlI",
       request = UserProfileRequest("108047156985872224463", "108047156985872224463") )
    }
}