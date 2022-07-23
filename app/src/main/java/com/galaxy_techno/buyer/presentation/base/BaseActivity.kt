package com.galaxy_techno.buyer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding

typealias InflateActivity<T> = (LayoutInflater) -> T

open class BaseActivity<VB : ViewBinding>(
    private val inflate: InflateActivity<VB>
) : AppCompatActivity() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    open fun initialize() {}
    open fun setUpListener() {}
    open fun setUpView() {}
    open fun observe() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        initialize()
        setUpListener()
        setUpView()
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
