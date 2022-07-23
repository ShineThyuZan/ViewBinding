package com.galaxy_techno.seller.presentation.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.galaxy_techno.buyer.presentation.base.InflateSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias InflateDialog<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseCustomDialog<VB : ViewBinding>(
    private val inflate: InflateSheet<VB>
) : DialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    open fun onCreateDialog(){}
    open fun initialize() {}
    open fun setupListener() {}
    open fun setupView() {}
    open fun observe() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        setupView()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}