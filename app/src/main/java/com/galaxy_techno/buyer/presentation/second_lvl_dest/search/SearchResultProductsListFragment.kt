package com.galaxy_techno.buyer.presentation.second_lvl_dest.search

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentSearchResultProductsBinding
import com.galaxy_techno.buyer.model.dto.Movie
import com.galaxy_techno.buyer.model.dto.SearchTypeAndResultModel
import com.galaxy_techno.buyer.presentation.base.SecondLvlFragment
import com.galaxy_techno.buyer.presentation.product.RemoteViewModel
import com.galaxy_techno.buyer.ui.adapter.MovieAdapter
import com.galaxy_techno.buyer.ui.delegate.MovieDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultProductsListFragment :
    SecondLvlFragment<FragmentSearchResultProductsBinding>(FragmentSearchResultProductsBinding::inflate) {

    private val args: SearchResultProductsListFragmentArgs by navArgs()
    private lateinit var searchTypeAndResultModel: SearchTypeAndResultModel
    var productId: String? = null
    private val vm: RemoteViewModel by viewModels()
    private var movieAdapter: MovieAdapter? = null
    private var pageNumber = 1
    private var firstTime = true
    private var isLoading = false
    private var KEY = "cdbea55de27a909b4aaa2cfc02eabb75"
    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.movieRecycler)
    }

    override fun initialize() {
        super.initialize()
        pageNumber = 1
        isLoading = true
        firstTime = true
        vm.getMovie(KEY, pageNumber++)
        movieAdapter = MovieAdapter(requireContext(), object : MovieDelegate {
            override fun onClickedMovieDelegate(notification: Movie) {
                Toast.makeText(context, "Movie ", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_dest_search_result_to_detail_list)
            }

            override fun loadMore() {
                vm.getMovie(KEY, pageNumber++)
                isLoading = true
                movieAdapter!!.isLoading(isLoading)
            }
        })

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        movieAdapter!!.clearData()
        movieAdapter!!.isLoading(isLoading)

        binding.ivFilter.setOnClickListener {
            //showCommentSettings()
            val directions =
                SearchResultProductsListFragmentDirections.actionDestSearchResultToProductFilter(
                    productId!!
                )
            findNavController().navigate(directions)
        }
    }

    override fun setUpView() {
        super.setUpView()
        binding.tvSearchText.text = args.searchResultText
        productId = args.searchResultText
        Toast.makeText(context, args.searchResultText, Toast.LENGTH_SHORT).show()
        //todo need to input product Id in navigation to product detail

    }


    override fun setUpListener() {
        super.setUpListener()
        binding.tvSearchText.setOnClickListener {
            // findNavController().navigate(R.id.action_dest_search_result_product_to_dest_search)
            navigateToSearchPage()
        }
        binding.ivBack.setOnClickListener {
            navigateToSearchPage()
        }
    }

    private fun navigateToSearchPage() {
        findNavController().popBackStack()
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.movieList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {
//                        binding.progress.visibility = View.VISIBLE
//                        binding.rvMainCategoryItems.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        //   binding.progress.visibility = View.GONE
                        isLoading = false
                        movieAdapter!!.isLoading(isLoading)
                        if (it.data!!.results.isEmpty()) {
                            movieAdapter!!.isLastPage(true)
                            if (firstTime) {
                                isLoading = true
                                movieAdapter!!.isLoading(isLoading)
                            }
                        } else {
                            firstTime = false
                            if (it.data.results.size < 10) {
                                movieAdapter!!.isLastPage(true)
                            } else {
                                movieAdapter!!.isLastPage(false)
                            }
                            movieAdapter!!.appendNewData(it.data.results)
                        }
                    }
                    else -> {}
                }
            }
        }
    }


}