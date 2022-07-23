package com.galaxy_techno.buyer.presentation.top_lvl_dest.home
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentTopHomeBinding
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.model.dto.Movie
import com.galaxy_techno.buyer.presentation.base.TopLvlFragment
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.presentation.product.ProductViewModel
import com.galaxy_techno.buyer.presentation.product.RemoteViewModel
import com.galaxy_techno.buyer.ui.adapter.MainCategoryProductsAdapter
import com.galaxy_techno.buyer.ui.adapter.MovieAdapter
import com.galaxy_techno.buyer.ui.adapter.MoviePagingDataAdapter
import com.galaxy_techno.buyer.ui.delegate.CategoryNameDelegate
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.galaxy_techno.buyer.ui.delegate.MovieDelegate
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TopHomeFragment : TopLvlFragment<FragmentTopHomeBinding>(FragmentTopHomeBinding::inflate),
    CategoryNameDelegate, DiscountProductDelegate {
    private val homeViewModel: TopHomeViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private var mainCategoryAdapter: MainCategoryProductsAdapter? = null
    private var adapter: MoviePagingDataAdapter? = null
    private var movieAdapter: MovieAdapter? = null
    private val vm: RemoteViewModel by viewModels()
    private var pageNumber = 1
    private var firstTime = true
    private var isLoading = false
    private  var KEY = "cdbea55de27a909b4aaa2cfc02eabb75"

    private val recyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.remote_recycler)
    }

    override fun initialize() {
        super.initialize()
        productViewModel.getMainCategoryItems()
        /** paging adapter setUp api call
        vm.getPagerMovies()
        setUpVerticalRViewForProducts()
         */
        pageNumber = 1
        isLoading = true
        firstTime = true
        vm.getMovie(KEY, pageNumber++)
        // binding.swipeRefresh.setOnRefreshListener(this)

        movieAdapter = MovieAdapter(requireContext(), object : MovieDelegate {
            override fun onClickedMovieDelegate(notification: Movie) {
                Toast.makeText(context, "Movie ", Toast.LENGTH_SHORT).show()
            }

            override fun loadMore() {
                vm.getMovie(KEY, pageNumber++ )
                isLoading = true
                movieAdapter!!.isLoading(isLoading)
            }
        })

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        movieAdapter!!.clearData()
        movieAdapter!!.isLoading(isLoading)
    }

    override fun setUpListener() {
        super.setUpListener()
      binding.customToolbar.searchHolder.setOnClickListener {
            findNavController().navigate(R.id.action_dest_home_to_search)
        }

        binding.customToolbar.imgCamera.setOnClickListener {
            requireContext().displayToast("Search with Camera")
        }
        binding.customToolbar.imgMic.setOnClickListener {
            requireContext().displayToast("Voice Search")
        }
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            productViewModel.productList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        //show error
                    }
                    is UiState.Loading -> {
              //        binding.swipeRefresh.isRefreshing = true
                        binding.progress.visibility = View.VISIBLE
                               binding.rvMainCategoryItems.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        if (it.data!!.success) {
                 //         binding.swipeRefresh.isRefreshing = false
                            Timber.tag("state").d(it.data.data.size.toString())
                            binding.progress.visibility = View.GONE
                            binding.rvMainCategoryItems.visibility = View.VISIBLE

                            /** setupRecyclerView */
                            setupRecyclerView()
                            mainCategoryAdapter?.setNewData(it.data.data as MutableList<ItemDTO.App>)
                        }
                    }
                    else -> {}
                }
            }
        }

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
                        binding.progress.visibility = View.VISIBLE
                           binding.rvMainCategoryItems.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progress.visibility = View.GONE
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

        /** data observe in paging adapter
       viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.movies.collectLatest {
                adapter?.submitData(it)
            }
        }
         */
    }

    private fun setupRecyclerView() {
        mainCategoryAdapter = MainCategoryProductsAdapter(requireContext(), this@TopHomeFragment, this)
        binding.rvMainCategoryItems.apply {
            this.adapter = mainCategoryAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    override fun onClickedName(categoryName: ItemDTO.App) {
        Toast.makeText(context, categoryName.category, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_dest_top_home_to_dest_all_category)

    }

    override fun onProductOnClick(app: Apps) {
        //todo need to input productId in navigation to detail screen
        findNavController().navigate(R.id.action_dest_home_to_detail_list)
    }


    /** paging adapter setUp
    private fun setUpVerticalRViewForProducts() {
        adapter = MoviePagingDataAdapter { getItemClick(it) }.apply {
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2)
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.adapter = this
            recyclerView.adapter = withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter(this),
                footer = MovieLoadStateAdapter(this)
            )
        }
    }
    private fun getItemClick(position: Int) {
        val item = adapter?.getClickItem(position)
        item?.let {
            Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
        }
    }
     */
}