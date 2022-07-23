package com.galaxy_techno.buyer.presentation.country_list

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentCountryDataBinding
import com.galaxy_techno.buyer.model.dto.State
import com.galaxy_techno.buyer.presentation.base.AuthFragment
import com.galaxy_techno.buyer.ui.adapter.StateListAdapter
import com.galaxy_techno.buyer.ui.delegate.StateDelegate
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountryListFragment : AuthFragment<FragmentCountryDataBinding>(FragmentCountryDataBinding::inflate), StateDelegate {

    private val viewModel: CountryListViewModel by activityViewModels()

    override fun initialize() {
        super.initialize()

        /** customDialog Testing */
        showCustomDialog()
        viewModel.getStateList()
    }

    private var stateListAdapter: StateListAdapter? = null


    private fun showCustomDialog() {
        val closeBtn: ImageView
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /** animate dialog with window animation */
        dialog.window!!.setWindowAnimations(R.style.AnimationForDialog)
        dialog.setCanceledOnTouchOutside(false)
        closeBtn = dialog.findViewById(R.id.imageView) as ImageView
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.countryObj.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Success -> {
         //               binding.tvCountryName.text = it.data!!.data.countryList[0].country
                    }
                    else -> {
                        binding.tvCountryName.text = "still loading"
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.stateList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.recyclerview.visibility = View.INVISIBLE
                        //just show progress bar
                    }
                    is UiState.Success -> {
                        if (it.data!!.status == Constant.STATUS_SUCCESS) {
                            Timber.tag("state").d(it.data.data!!.stateList.size.toString())
                            binding.progress.visibility = View.GONE
                            binding.recyclerview.visibility = View.VISIBLE

                            /** setupRecyclerView */
                            setupRecyclerView()
                            stateListAdapter?.setNewData(it.data.data.stateList as MutableList<State>)
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        stateListAdapter = StateListAdapter(context!!, this@CountryListFragment)
        binding.recyclerview.apply {
            this.adapter = stateListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClick(state: State) {
    }


}