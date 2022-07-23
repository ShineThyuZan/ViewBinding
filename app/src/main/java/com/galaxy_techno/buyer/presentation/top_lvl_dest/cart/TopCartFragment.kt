package com.galaxy_techno.buyer.presentation.top_lvl_dest.cart
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentTopCartBinding
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.presentation.base.TopLvlFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopCartFragment : TopLvlFragment<FragmentTopCartBinding>(FragmentTopCartBinding::inflate),
    CartProductClickDelegate {

    private val cartViewModel: TopCartViewModel by viewModels()
    private lateinit var topCartAdapter: TopCartAdapter

    override fun setUpView() {
        super.setUpView()
        binding.toolbar.tvToolbarTitle.text = "My Cart"
    }

    override fun initialize() {
        super.initialize()
        cartViewModel.getCountryList()
    }

    override fun observe() {
        super.observe()
        cartViewModel.cartProductObj.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    requireContext().displayToast("data get in success")
                    setUpCountryListRecyclerView()
                    topCartAdapter.setNewData(it.data!!.data as MutableList<CountryVos>)
                }

                else -> {
                    Toast.makeText(context, "fail ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpCountryListRecyclerView() {
        topCartAdapter = TopCartAdapter(requireContext(), this@TopCartFragment)
       binding.rvTopCart.apply {
            this.adapter = topCartAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClickCartView(data: CountryVos) {
        requireContext().displayToast(data.name)
    }

    override fun onClickAddButton(data: CountryVos, count: Int) {
        requireContext().displayToast(data.name)
    }

    override fun onClickMinusButton(data: CountryVos, count: Int) {
        requireContext().displayToast(data.name)
    }

    override fun onClickMore(data: CountryVos) {
        requireContext().displayToast(data.name)
    }

}