package com.galaxy_techno.buyer.presentation.auth.otp

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.InternetChecker
import com.galaxy_techno.buyer.common.NetworkStatus
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentRegisterVerifyOtpBinding
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.auth.login.LoginFragmentDirections
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.presentation.extension.*
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterVerifyOtpFragment : AuthFragment<FragmentRegisterVerifyOtpBinding>(
    FragmentRegisterVerifyOtpBinding::inflate
) {

    //klt 15.1.22
    private val args: RegisterVerifyOtpFragmentArgs by navArgs()

    private val viewModel: AuthViewModel by activityViewModels()
    private var phoneNumber: String? = ""

    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false

    override fun setUpView() {
        super.setUpView()
        setupRequestedPhone()
        clearErrorOnFocus()
    }

    @SuppressLint("SetTextI18n")
    private fun setupRequestedPhone() {
        binding.etRegisterPhone.setText(args.phone)
    }

    override fun setUpListener() {
        super.setUpListener()

        binding.btnRegisterGetCode.setOnClickListener {
            if (!internetStatus) {
                binding.root.displaySnack(getString(R.string.snack_no_internet))
            } else {
                viewModel.setPhone(
                    binding.etRegisterPhone.text?.trim().toString()
                )
                viewModel.requestOTP(
                    binding.etRegisterPhone.text?.trim().toString()
                )
            }
            viewModel.setOtpLoadingState(true)
        }

        binding.btnRegisterOtpVerify.setOnClickListener {

            if (validate()) {
                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    viewModel.validateOTP(
                        binding.etRegisterPhone.text?.trim().toString(),
                        binding.etRegisterOtpCode.text?.trim().toString()
                    )
                    viewModel.setVerifyingLoadingState(true)
                }
            }
        }

        binding.clOtp.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.clOtp && hasFocus)
                hideKeyBoard()
        }

        binding.etRegisterPhone.addTextChangedListener {
            binding.tilRegisterPhone.setCloseable(it)
            it?.let { text ->
                binding.btnRegisterGetCode.isVisible = text.isVerifiedPhone()
            }
        }

    }

    override fun observe() {
        super.observe()
        internetChecker.observe(viewLifecycleOwner) {
            internetStatus = when (it) {
                NetworkStatus.Available -> {
                    true
                }
                NetworkStatus.UnAvailable -> {
                    false
                }
            }
        }


        //otp request
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.responseOTP.collect {
                when (it) {

                    is UiState.Empty -> {
                        //Unit
                    }
                    is UiState.Error -> {
                        binding.root.displaySnack(getString(R.string.snack_request_otp_fail))
                    }
                    is UiState.Loading -> {
                        viewModel.setOtpLoadingState(true)
                    }
                    is UiState.Fail -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is UiState.Success -> {
                        val response = it.data?.data!!
                        if(response.isRegister) {
                            navigateToAlreadyRegister()
                        } else {
                            binding.root.displaySnack(getString(R.string.snack_request_otp_success))
                            viewModel.setTimer(response!!.expireTimeMin)

                            val code = response.otpCode
                            delay(2000L)
                            binding.etRegisterOtpCode.setText(code)
                        }
                    }
                }
            }
        }

        //validation response
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.responseOTPValidation.collect {
                when (it) {
                    is UiState.Empty -> {
                        //Unit
                    }
                    is UiState.Loading -> {
                        viewModel.setVerifyingLoadingState(true)
                    }
                    is UiState.Error -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_otp_verify_fail)
                        )
                    }
                    is UiState.Fail -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is UiState.Success -> {
                        binding.root.displaySnack(getString(R.string.snack_request_otp_verify_success))
                        binding.btnRegisterOtpVerify.setActive(false)
                        delay(100L)
                        navigateToRegister()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isVerifyingLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_verify_otp))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.timer.collectLatest {
                checkOTPButtonState(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isOtpLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog((getString(R.string.dialog_get_otp)))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

    }

    private fun navigateToRegister() {
        viewModel.setVerifyingLoadingState(false)
        val registerPhone: String = viewModel.phoneNumber.toString()
        val directions =
            RegisterVerifyOtpFragmentDirections.actionRegisterVerifyOtpFragmentToRegisterFragment(
                registerPhone
            )
        val registerNavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.registerFragment, true)
            .build()
        findNavController().navigate(directions, registerNavOptions)
    }

    private fun navigateToAlreadyRegister() {
        val directions =
            RegisterVerifyOtpFragmentDirections.actionRegisterVerifyOtpFragmentToRegisterFragment(
                binding.etRegisterPhone.text?.trim().toString()
            )
        findNavController().navigate(directions)
    }

    private fun checkOTPButtonState(count: Int) {
        binding.btnRegisterGetCode.setActive(count == 0)
        binding.etRegisterPhone.setActive(count == 0)
        binding.tilRegisterPhone.setCloseable(null)
        if (count != 0) {
            binding.btnRegisterGetCode.text = count.toString()
        } else binding.btnRegisterGetCode.text = getString(R.string.btn_get_code)
    }

    private fun validate(): Boolean {
        if (!binding.etRegisterPhone.isVerifiedPhone()) {
            binding.tilRegisterPhone.error = getString(R.string.error_register_phone)
        }
        if (!binding.etRegisterOtpCode.isVerifyName()) {
            binding.tilRegisterOtpCode.error = getString(R.string.error_register_otp)
            return false
        }
        return true
    }


    private fun clearErrorOnFocus() {
        binding.etRegisterPhone.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterPhone && hasFocus)
                binding.tilRegisterPhone.isErrorEnabled = !hasFocus
        }
        binding.etRegisterOtpCode.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterOtpCode && hasFocus)
                binding.tilRegisterOtpCode.isErrorEnabled = !hasFocus
        }
    }
}