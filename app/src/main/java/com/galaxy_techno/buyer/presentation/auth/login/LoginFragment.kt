package com.galaxy_techno.buyer.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.InternetChecker
import com.galaxy_techno.buyer.common.NetworkStatus
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentLoginBinding
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.extension.*
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : SecondLvlFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false
    private var argsSelectedCode: String = ""

    override fun setUpView() {
        super.setUpView()
        clearErrorOnFocus()
        binding.toolbar.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.etPhone.setText(viewModel.phoneNumber.value)
        if (binding.etPhone.text!!.isNotEmpty()) {
            binding.tilPhone.setCloseable(binding.etPhone.text)
        }

        binding.etPhone.addTextChangedListener {
            if (binding.etPhone.text.isNullOrEmpty()) {
                binding.tilPhone.clearCloseable()
            } else {
                binding.tilPhone.setCloseable(it)
            }
        }
    }

    override fun initialize() {
        super.initialize()
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //   .requestIdToken(SERVER_CLIENT_ID)
            .requestIdToken("482960356014-pgd2tdbev305jj51r54usvn46a9hrijc.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the option specified by gso
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

    }

    /** firebase current user update
    override fun onStart() {
    super.onStart()
    val currentUser = auth.currentUser
    updateUI(currentUser)
    }*/

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /** Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...); */
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                /** Google Sign In was successful, authenticate with Firebase */
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
                requireContext().displayToast(account.displayName.toString())

            } catch (e: ApiException) {
                /** Google Sign In failed, update UI appropriately */
                Toast.makeText(context, "sign in fail ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {

                    /** Sign in success, update UI with the signed-in user's information */
                    val user = firebaseAuth.currentUser
                    val userId = firebaseAuth.uid
                    val email = user!!.email

                    requireContext().displayToast(email.toString())
//                  updateUI(user)

                } else {
                    /** If sign in fails, display a message to the user. */
                    Timber.tag(TAG).w(authResult.exception)
                    updateUI(null)
                }
            }
            .addOnFailureListener { e ->
                requireContext().displayToast("fail in email login ")
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {

    }


    private fun showToast(msg: String) {
        requireContext().displayToast(msg)
    }

    override fun setUpListener() {
        super.setUpListener()

        binding.ivFacebook.setOnClickListener {
            showToast("Facebook Login")
        }

        binding.ivWeChat.setOnClickListener {
            showToast("Wechat Login")
        }
        /** login with Google */
        binding.ivGoogle.setOnClickListener {
            signIn()
        }
        binding.btnCountry.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_selected_country)
        }


        /** login to selected Country bottom Sheet */
        binding.btnLogoutInProfile.setOnClickListener {
            if (validate()) {
                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    viewModel.setLoginLoadingState(true)
                    viewModel.requestLogin(
                        binding.etPhone.text?.trim().toString(),
                        binding.etPwd.text?.trim().toString()
                    )
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val directions =
                LoginFragmentDirections.actionLoginFragmentToRegisterVerifyOtpFragment(
                    binding.etPhone.text?.trim().toString()
                )
            findNavController().navigate(directions)
        }

        binding.tvForgetPwd.setOnClickListener {
            val directions =
                LoginFragmentDirections.actionLoginFragmentToPasswordResetOtpFragment(
                    binding.etPhone.text?.trim().toString()
                )
            findNavController().navigate(directions)
        }

        binding.clLogin.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.clLogin && hasFocus)
                hideKeyBoard()
        }
    }

    override fun observe() {
        super.observe()

        viewModel.countryCode.observe(viewLifecycleOwner) {
            binding.btnCountry.text = it
        }

        internetChecker.observe(viewLifecycleOwner) {
            internetStatus = when (it) {
                NetworkStatus.Available -> {
                    true
                }
                NetworkStatus.UnAvailable -> {
                    false
                }
                else -> {
                    false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoginLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_login))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.responseLogin.collectLatest {
                when (it) {
                    is UiState.Empty -> {
                        Timber.tag(getString(R.string.empty_message))
                    }
                    is UiState.Loading -> {
                        viewModel.setLoginLoadingState(true)
                    }
                    is UiState.Error -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_login_fail)
                        )
                    }
                    is UiState.Fail -> {
                        binding.root.displaySnack(it.data?.error ?: " Error")
                    }
                    is UiState.Success -> {
                        binding.root.displaySnack(getString(R.string.snack_request_login_success))
                        binding.btnLogoutInProfile.setActive(false)
                        binding.btnRegister.setActive(false)

                        navigateToHome()
                    }
                }
            }
        }
    }


    private fun navigateToHome() {
        val authOptions = NavOptions.Builder()
            .setPopUpTo(R.id.dest_top_home, true)
            .build()
        findNavController().navigate(R.id.action_login_to_home, Bundle(), authOptions)
    }

    private fun validate(): Boolean {
        if (!binding.etPhone.isVerifiedPhone()) {

            binding.tilPhone.error = getString(R.string.error_login_phone)
            return false
        }

        if (!binding.etPwd.isVerifyPwd()) {
            binding.tilPassword.error = getString(R.string.error_login_pwd_8)
            return false
        }
        return true
    }

    private fun clearErrorOnFocus() {
        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPhone && hasFocus)
                binding.tilPhone.isErrorEnabled = !hasFocus
        }
        binding.etPwd.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPwd && hasFocus)
                binding.tilPassword.isErrorEnabled = !hasFocus
        }
    }

}