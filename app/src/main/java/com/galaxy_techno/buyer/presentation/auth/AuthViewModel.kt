package com.galaxy_techno.buyer.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.di.Qualifier
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import com.galaxy_techno.buyer.model.dto.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository,
    @Qualifier.Io private val io: CoroutineDispatcher
) : ViewModel() {

    init {
        getCountryList()
    }

    /** country code api call */
    private val _countryObj = MutableLiveData<UiState<CountryListResponse>>()
    val countryObj: LiveData<UiState<CountryListResponse>> get() = _countryObj
    private fun getCountryList() {
        viewModelScope.launch {
            appRepository.getCountryList().collect {
                _countryObj.value = it
            }
        }
    }

    /** country code re-set to Login Screen when country filter bottom sheet */
    private val _countryCode = MutableLiveData<String>()
    val countryCode: LiveData<String> get() = _countryCode
    fun setCountryCode(code: String) {
        viewModelScope.launch {
            _countryCode.postValue(code)
        }
    }

    private val _countryName = MutableLiveData<String>()
    val countryName: LiveData<String> get() = _countryName
    fun setCountryName(country: String) {
        viewModelScope.launch {
            _countryName.postValue(country)
            Timber.tag("country").d("country$country")
        }
    }

    private var _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    private var _timer = MutableStateFlow(0)
    val timer get() = _timer

    //phone no is save in VM for register
    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private var _isOtpLoading = MutableStateFlow<Boolean>(false)
    val isOtpLoading get() = _isOtpLoading.asStateFlow()

    private var _isVerifyingLoading = MutableStateFlow<Boolean>(false)
    val isVerifyingLoading get() = _isVerifyingLoading.asStateFlow()

    /** loading for saveState in screen rotation */
    private var _isLoginLoading = MutableStateFlow<Boolean>(false)
    val isLoginLoading get() = _isLoginLoading.asStateFlow()

    private var _isRegisterLoading = MutableStateFlow<Boolean>(false)
    val isRegisterLoading get() = _isRegisterLoading.asStateFlow()

    private var _isPasswordForgetLoading = MutableStateFlow<Boolean>(false)
    val isPasswordForgetLoading get() = _isPasswordForgetLoading.asStateFlow()

    private var _isPasswordAddNewLoading = MutableStateFlow<Boolean>(false)
    val isPasswordAddNewLoading get() = _isPasswordAddNewLoading.asStateFlow()

    fun setTimer(minute: Int) {
        val counter = minute * 60
        if (_timer.value == 0) {
            viewModelScope.launch {
                repeat(counter) {
                    _timer.value = (counter - it)
                    delay(1000L)
                }
                _timer.value = 0
            }
        }
    }

    fun setPhone(phoneNumber: String) {
        viewModelScope.launch {
            _phoneNumber.postValue(phoneNumber)
        }
    }

    fun setOtpLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isOtpLoading.value = state
        }
    }

    fun setVerifyingLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isVerifyingLoading.emit(state)
        }
    }

    fun setLoginLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isLoginLoading.emit(state)
        }
    }

    fun setRegisterLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isRegisterLoading.emit(state)
        }
    }

    fun setPasswordForgetLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isPasswordForgetLoading.emit(state)
        }
    }

    fun setPasswordAddNewLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isPasswordAddNewLoading.emit(state)
        }
    }

    private val _responseLogin = MutableSharedFlow<UiState<LoginDTO>>()
    val responseLogin get() = _responseLogin.asSharedFlow()

    fun requestLogin(
        phone: String,
        password: String
    ) {
        val mobileNumber = "+959$phone"
        viewModelScope.launch {

            _responseLogin.emit(UiState.Loading())

            userRepository.login(
                mobileNumber,
                password
            ).collect {

                delay(1000L)

                val dto = it.data
                val userData = it.data?.data

                userData?.let {
                    //Server response 200 with Status = "Success"
                    if (dto?.status == Constant.STATUS_SUCCESS) {
                        setLoginLoadingState(false)
                        _responseLogin.emit(UiState.Success(dto))
                        saveAuthState(Constant.AUTH_LOGGED_IN)
                        saveUserEntity(dto.data!!)
                    } else {
                        //Server response 200 with Status = "Fail"
                        setLoginLoadingState(false)
                        _responseLogin.emit(UiState.Fail(dto!!))
                    }
                } ?: run {
                    //Server response 400-500 with error message
                    setLoginLoadingState(false)
                    _responseLogin.emit(UiState.Error(it.message))
                }

            }
        }
    }

    /** OTP requesting */
    private val _responseOTP = MutableSharedFlow<UiState<OTPRequestDTO>>()
    val responseOTP get() = _responseOTP.asSharedFlow()

    fun requestOTP(mobile: String) {
        val mobileNumber = "+959$mobile"
        viewModelScope.launch {
            _responseOTP.emit(UiState.Loading())
            delay(1000L)
            userRepository.requestOTP(
                mobileNumber
            ).collect {
                val dto = it.data
                val otpResponse = it.data?.data

                otpResponse?.let {
                    //Server response 200 with Status = "Success"
                    if (dto?.status == Constant.STATUS_SUCCESS) {
                        setOtpLoadingState(false)
                        _responseOTP.emit(UiState.Success(dto))
                    } else {
                        //Server response 200 with Status = "Fail"
                        setOtpLoadingState(false)
                        _responseOTP.emit(UiState.Fail(dto!!))
                    }
                } ?: run {
                    //Server response 400-500 with error message
                    setOtpLoadingState(false)
                    _responseOTP.emit(UiState.Error(it.message))
                }
            }
        }
    }

    // OTP validation
    private val _responseOTPValidation = MutableSharedFlow<UiState<OTPValidateDTO>>()
    val responseOTPValidation get() = _responseOTPValidation.asSharedFlow()

    fun validateOTP(mobile: String?, otpCode: String) {
        val mobileNumber = "+959$mobile"
        viewModelScope.launch {
            //after request : response emit Loading first
            _responseOTPValidation.emit(UiState.Loading())
            delay(2000L)
            userRepository.validateOTP(
                mobileNumber,
                otpCode
            ).collect {

                val dto = it.data
                val validateData = it.data?.data

                validateData?.let {
                    //Server response 200 with Status = "Success"
                    if (dto?.status == Constant.STATUS_SUCCESS) {
                        setVerifyingLoadingState(false)
                        _responseOTPValidation.emit(UiState.Success(dto))
                    } else {
                        //Server response 200 with Status = "Fail"
                        setVerifyingLoadingState(false)
                        _responseOTPValidation.emit(UiState.Fail(dto!!))
                    }
                } ?: run {
                    //Server response 400-500 with error message
                    setVerifyingLoadingState(false)
                    _responseOTPValidation.emit(UiState.Error(it.message))
                }
            }
        }
    }

    /** register */
    private val _responseRegister = MutableSharedFlow<UiState<RegisterDTO>>()
    val responseRegister get() = _responseRegister.asSharedFlow()
    fun requestRegister(
        name: String,
        email: String,
        gender: String,
        mobile: String,
        password: String
    ) {
        val mobileNumber = "+959$mobile"

        viewModelScope.launch {
            _responseRegister.emit(UiState.Loading())
            userRepository.register(
                name,
                email,
                gender,
                mobileNumber,
                password
            ).collect {

                delay(1000L)

                val dto = it.data
                val userData = it.data?.data

                userData?.let {
                    //Server response 200 with Status = "Success"
                    if (dto?.status == Constant.STATUS_SUCCESS) {
                        requestLogin(mobile, password)
                    } else {
                        //Server response 200 with Status = "Fail"
                        setRegisterLoadingState(false)
                        setLoginLoadingState(false)
                        _responseRegister.emit(UiState.Fail(dto!!))
                    }
                } ?: run {
                    //Server response 400-500 with error message
                    setRegisterLoadingState(false)
                    setLoginLoadingState(false)
                    _responseRegister.emit(UiState.Error(it.message))
                }
            }
        }
    }

    //pwd forget
    private val _responsePwdForget = MutableStateFlow(UiState.Empty<LoginDTO>())
    val responsePwdForget get() = _responsePwdForget.asStateFlow()

    fun requestPwsForget() {
        viewModelScope.launch {
            // do request to userRepo
        }
    }

    fun requestPwdAddNew() {
        viewModelScope.launch {
            // do request to userRepo
        }
    }

    private fun saveAuthState(state: Boolean) {
        viewModelScope.launch(io) {
            appRepository.storeAuthState(state)
            /*when (state) {
                */
            /** loginState === ture
            logoutState === false *//*
                Constant.AUTH_LOGGED_IN -> appRepository.storeAuthState(true)
                Constant.AUTH_LOGGED_OUT -> appRepository.storeAuthState(false)
            }*/
        }
    }

    private fun saveUserEntity(user: UserData) {
        viewModelScope.launch {
            val entity = user.toEntity()
            userRepository.saveUser(entity)
        }
    }

    fun setTokenToDs(token: String) {
        viewModelScope.launch {
            appRepository.setToken(token)
        }
    }
}



