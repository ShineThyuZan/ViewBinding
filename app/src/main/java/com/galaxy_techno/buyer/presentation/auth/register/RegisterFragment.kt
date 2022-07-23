package com.galaxy_techno.buyer.presentation.auth.register

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.InternetChecker
import com.galaxy_techno.buyer.common.NetworkStatus
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentRegisterBinding
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.presentation.extension.*
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : AuthFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var firebaseAuth: FirebaseAuth


    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false

    override fun setUpView() {
        super.setUpView()
        clearErrorOnFocus()

    }

    override fun initialize() {
        super.initialize()
        firebaseAuth = FirebaseAuth.getInstance()

        /** firebaseAuth.signOut()*/
        checkUser()
    }
    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if ( firebaseUser == null){
            requireContext().displayToast("firebase User null state")
        }
        else {
            val email = firebaseUser.email
        }
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.btnConfirmRegister.setOnClickListener {

            if (validate()) {
                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    val genderValue =
                        if (binding.rbRegisterMale.isChecked) "1" else "0" //male : 1, female : 0
                    viewModel.requestRegister(
                        binding.etRegisterName.text?.trim().toString(),
                        binding.etRegisterEmail.text?.trim().toString(),
                        genderValue,
                        binding.etRegisterPhone.text?.trim().toString(),
                        binding.etRegisterPassword.text?.trim().toString(),
                    )
                }
            }
        }

        binding.btnTerms.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_terms_Fragment)
        }
        binding.clRegister.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.clRegister && hasFocus)
                hideKeyBoard()
        }
    }

    override fun observe() {
        super.observe()

        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            setupPhoneNumber(it)
        }

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

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoginLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_register))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.responseRegister.collectLatest {
                when (it) {

                    is UiState.Empty -> {
                        Timber.tag("empty state")
                    }
                    is UiState.Loading -> {
                        viewModel.setLoginLoadingState(true)
                    }
                    is UiState.Error -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_register_fail)
                        )
                    }
                    is UiState.Fail -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is UiState.Success -> {
                        viewModel.setLoginLoadingState(true)
                    }

                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.responseLogin.collectLatest {
                when (it) {
                    is UiState.Empty -> {
                        Timber.tag("empty state")
                    }
                    is UiState.Error -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_register_fail)
                        )
                    }
                    is UiState.Loading -> {
                        viewModel.setLoginLoadingState(true)
                    }
                    is UiState.Fail -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is UiState.Success -> {
                        binding.root.displaySnack(getString(R.string.snack_request_register_success))
                        binding.btnConfirmRegister.setActive(false)
                        delay(500L)
                        navigateToFavourite()
                    }
                }
            }
        }
    }

    private fun navigateToFavourite() {
        val favNavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.registerFragment, true)
            .build()
        findNavController().navigate(
            R.id.action_registerFragment_to_favouriteCategoryFragment,
            Bundle(),
            favNavOptions
        )
    }

    private fun validate(): Boolean {

        if (!binding.etRegisterName.isVerifyName()) {
            binding.tilRegisterName.error = getString(R.string.error_register_name)
            return false
        }

        if (!binding.etRegisterEmail.isVerifyEmail()) {
            binding.tilRegisterEmail.error = getString(R.string.error_register_email)
            return false
        }

        if (!binding.etRegisterPhone.isVerifiedPhone()) {
            binding.tilRegisterPhone.error = getString(R.string.error_register_phone)
            return false
        }

        if (!binding.rbRegisterMale.isChecked && !binding.rbRegisterFemale.isChecked) {
            binding.root.displaySnack(
                requireContext().getString(R.string.select_gender_warning)
            )
            return false
        }
        if (!binding.etRegisterPassword.isVerifyPwd()) {
            binding.tilRegisterPassword.error = getString(R.string.error_register_pwd)
            return false
        }
        if (!binding.etRegisterConfirmPassword.isVerifyPwd()) {
            binding.tilRegisterConfirmPassword.error = getString(R.string.error_register_pwd)
            return false
        }
        if (!binding.etRegisterConfirmPassword.isIdentifiedPwd(binding.etRegisterPassword.text.toString())) {
            binding.tilRegisterConfirmPassword.error =
                getString(R.string.error_register_pwd_confirm)
            return false
        }
        if (!binding.cbTerms.isChecked) {
            binding.root.displaySnack(
                requireContext().getString(R.string.snack_terms)
            )
            return false
        }

        return true
    }

    private fun clearErrorOnFocus() {

        binding.etRegisterName.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterName && hasFocus)
                binding.tilRegisterName.isErrorEnabled = !hasFocus
        }
        binding.etRegisterEmail.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterEmail && hasFocus)
                binding.tilRegisterEmail.isErrorEnabled = !hasFocus
        }

        binding.etRegisterPassword.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterPassword && hasFocus)
                binding.tilRegisterPassword.isErrorEnabled = !hasFocus
        }
        binding.etRegisterConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etRegisterConfirmPassword && hasFocus)
                binding.tilRegisterConfirmPassword.isErrorEnabled = !hasFocus
        }
    }


    private fun setupPhoneNumber(phone: String?) {
        binding.etRegisterPhone.setText(phone)
        binding.etRegisterPhone.setActive(false)
    }


}

