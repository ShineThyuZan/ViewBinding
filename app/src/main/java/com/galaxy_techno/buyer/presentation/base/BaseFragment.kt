package com.galaxy_techno.buyer.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import timber.log.Timber

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

//just inflate xml to binding
open class BaseFragment<VB : ViewBinding>(
    private val inflate: InflateFragment<VB>

) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    open fun doLogic() {}
    open fun initialize() {}
    open fun setUpListener() {}
    open fun setUpView() {}
    open fun observe() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        doLogic()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpListener()
        setUpView()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun hideKeyBoard() {
        // Hide the keyboard
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        Timber.tag("Hide Keyboard").d("Hide True")
    }
}

//bottom navigation bar is hidden in auth graph
//toolbar is shown
open class AuthFragment<VB : ViewBinding>(
    inflate: InflateFragment<VB>
) : BaseFragment<VB>(inflate) {

    override fun doLogic() {
        super.doLogic()
        (activity as MainActivity).shouldShowBottomNavigation(false)
        (activity as MainActivity).shouldShowToolbar(true)
    }
}

//bottom navigation bar is shown in top lvl destinations
//toolbar is hidden
open class TopLvlFragment<VB : ViewBinding>(
    inflate: InflateFragment<VB>
) : BaseFragment<VB>(inflate) {
    override fun doLogic() {
        super.doLogic()
        (activity as MainActivity).shouldShowBottomNavigation(true)
        (activity as MainActivity).shouldShowToolbar(false)
    }
}

//bottom navigation bar is hidden in other lvl destinations
open class SecondLvlFragment<VB : ViewBinding>(
    inflate: InflateFragment<VB>
) : BaseFragment<VB>(inflate) {
    private var _binding: VB? = null

    override fun doLogic() {
        super.doLogic()
        (activity as MainActivity).shouldShowBottomNavigation(false)
        (activity as MainActivity).shouldShowToolbar(false)
    }
}

open class TopLvlToolbarFragment<VB : ViewBinding>(
    inflate: InflateFragment<VB>
): BaseFragment<VB>(inflate){
    private var _binding : VB? = null
    override fun doLogic() {
        super.doLogic()
        (activity as MainActivity).shouldShowBottomNavigation(true)
        (activity as MainActivity).shouldShowToolbar(true)
    }
}

//bottom navigation bar is hidden in other lvl destinations
open class ToolbarHiddenFragment<VB : ViewBinding>(
    inflate: InflateFragment<VB>
) : BaseFragment<VB>(inflate) {
    private var _binding: VB? = null

    override fun doLogic() {
        super.doLogic()
        (activity as MainActivity).shouldShowBottomNavigation(false)
        (activity as MainActivity).shouldShowToolbar(false)
    }
}

