package com.galaxy_techno.buyer.presentation.auth.otp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.databinding.DialogAlreadyRegisterBinding
import com.galaxy_techno.buyer.presentation.auth.AuthViewModel
import com.galaxy_techno.seller.presentation.base.BaseCustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNoAlreadyExistDialog : BaseCustomDialog<DialogAlreadyRegisterBinding>(DialogAlreadyRegisterBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()
    private val args: RegisterVerifyOtpFragmentArgs by navArgs()
    private var infoText: String? = null

    @SuppressLint("ResourceType")
    override fun initialize() {
        super.initialize()
        dialog?.setCanceledOnTouchOutside(false)
        infoText = getString(R.string.otp_info, args.phone)
    }


    override fun setupView() {
        super.setupView()
        dialog?.setContentView(R.layout.dialog_already_register)
        val btnCancel: Button = dialog!!.findViewById(R.id.btn_cancel) as Button
        var btnLogin: Button = dialog!!.findViewById(R.id.btnLogoutInProfile) as Button
        var textValue: TextView = dialog!!.findViewById(R.id.tv_otp_info) as TextView
        val window = dialog?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window!!.setWindowAnimations(R.style.AnimationForDialog)
        dialog?.show()
        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        btnLogin.setOnClickListener {
            viewModel.setPhone(args.phone)
            val directions =
                PhoneNoAlreadyExistDialogDirections.actionPhoneNoAlreadyExistDialogToLoginFragment(
                    args.phone)
            findNavController().navigate(directions)
        }
        textValue.text = infoText
    }

}


