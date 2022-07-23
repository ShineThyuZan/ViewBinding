package com.galaxy_techno.buyer.presentation.second_lvl_dest.search

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.databinding.FragmentSearchBinding
import com.galaxy_techno.buyer.model.dto.SearchProductResponse
import com.galaxy_techno.buyer.model.dto.SearchTypeAndResultModel
import com.galaxy_techno.buyer.model.dto.State
import com.galaxy_techno.buyer.presentation.base.ToolbarHiddenFragment
import com.galaxy_techno.buyer.presentation.country_list.CountryListViewModel
import com.galaxy_techno.buyer.presentation.extension.displaySnack
import com.galaxy_techno.buyer.presentation.extension.displayToast
import com.galaxy_techno.buyer.ui.adapter.SearchResultAdapter
import com.galaxy_techno.buyer.ui.adapter.StateListAdapter
import com.galaxy_techno.buyer.ui.adapter.TrendingAdapter
import com.galaxy_techno.buyer.ui.delegate.SearchResultDelegate
import com.galaxy_techno.buyer.ui.delegate.StateDelegate
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class SearchFragment : ToolbarHiddenFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    StateDelegate, SearchResultDelegate, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var searchTypeAndResultModel: SearchTypeAndResultModel
    val TYPE_TEXT_STATE = ""
    private var typeText: String? = null
    private val searchAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter(requireContext(), this).apply {
            binding.rvUserSearch.adapter = this
            binding.rvUserSearch.layoutManager = LinearLayoutManager(requireContext())
            binding.rvUserSearch.setHasFixedSize(true)

        }
    }

    override fun onClickSearchProductResult(result: SearchProductResponse.SearchResult) {
        requireContext().displayToast(result.userName)
        typeText = binding.customToolbar.searchView.toString()
        searchTypeAndResultModel = SearchTypeAndResultModel(
            result.userName,
            binding.customToolbar.searchView.toString()
        )
        val direction = SearchFragmentDirections.actionDestSearchToResult(typeText.toString())
        findNavController().navigate(direction)
    }

    private val countryListViewModel: CountryListViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val speechRecognizer by lazy { SpeechRecognizer.createSpeechRecognizer(requireContext()) }
    private var onPerformSpeech = true
    private var trendingAdapter: TrendingAdapter? = null
    private var stateListAdapter: StateListAdapter? = null
    private val handler: Handler = Handler()
    private val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Please tell me what you want to find!")
    }

    private val voicePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        it?.let {
            if (isVoiceEnabled()) {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    speechResult.launch(speechIntent)
                }
            } else binding.root.displaySnack("Your phone doesn't support Voice Search")
        }
    }

    private val speechResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it?.let {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data?.extras
                val msgArray = data?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val voiceMsg = msgArray?.size.toString()
                Timber.tag("seeeeresult").d(msgArray?.size.toString())
                speechRecognizer.startListening(speechIntent)
            }
        } ?: Timber.tag("seeeenull").d(it.toString())
    }


    override fun initialize() {
        super.initialize()
        setupSpeechRecognizer()
        countryListViewModel.getStateList()
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.customToolbar.imgBack.setOnClickListener {
            navigateToHome()
        }
        binding.customToolbar.imgCamera.setOnClickListener {
            requireContext().displayToast("Image Search")
        }
        binding.customToolbar.imgMic.setOnClickListener {
            requestVoicePermission()
        }
        binding.customToolbar.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                countryListViewModel.searchResult("117867598627360919374", 1, 10, query.toString())

                return true
            }

            override fun onQueryTextChange(inputSearchText: String?): Boolean {

                if (inputSearchText.isNullOrBlank()) {
                    binding.rvUserSearch.visibility = View.GONE
                    binding.nsv.visibility = View.VISIBLE
                    searchAdapter.clearData()
                } else {
                    handler.postDelayed({
                        run {
                            binding.nsv.visibility = View.GONE
                            binding.rvUserSearch.visibility = View.VISIBLE
                            typeText = inputSearchText.trim()

                            Timber.tag("typetype").d(typeText)
                            inputSearchText.let {
                                if (it.length >= 3) {

                                    // searchAdapter.clearData()
                                    countryListViewModel.searchResult(
                                        "117867598627360919374",
                                        1,
                                        10,
                                        inputSearchText.toString()
                                    )
                                }
                            }
                        }
                    }, 500)
                }
                return true
            }
        }
        )
    }

    private fun navigateToHome() {

        findNavController().popBackStack()
       
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopRecognition()
    }

    private fun isVoiceEnabled() = SpeechRecognizer.isRecognitionAvailable(requireContext())
    override fun observe() {
        super.observe()

        countryListViewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Empty -> {

                }
                is UiState.Error -> {

                }
                is UiState.Success -> {
                    binding.tvNoInternet.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    binding.rvUserSearch.visibility = View.VISIBLE
                    searchAdapter.setNewData(it.data?.data!! as MutableList<SearchProductResponse.SearchResult>)
                }
                is UiState.Fail -> {}
                is UiState.Loading -> {}

                else -> {}
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            countryListViewModel.stateList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {

                        Unit
                    }
                    is UiState.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        //show error
                    }
                    is UiState.Loading -> {

                        binding.progress.visibility = View.VISIBLE
                        binding.swipeRefresh.isRefreshing = true
                        //just show progress bar
                    }
                    is UiState.Success -> {
                        if (it.data!!.status == Constant.STATUS_SUCCESS) {
                            Timber.tag("state").d(it.data.data!!.stateList.size.toString())
//                            binding.progress.visibility = View.GONE
                            binding.swipeRefresh.isRefreshing = false
                            binding.rvRecent.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE

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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            countryListViewModel.stateList.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Empty -> {
                        Unit
                    }
                    is UiState.Error -> {
                        binding.tvNoInternet.visibility = View.VISIBLE
                        binding.nsv.visibility = View.GONE
                        binding.tvNoInternet.visibility = View.VISIBLE
                    }
                    is UiState.Loading -> {
                        binding.rvTrending.visibility = View.INVISIBLE
                        //just show progress bar
                    }
                    is UiState.Success -> {
                        if (it.data!!.status == Constant.STATUS_SUCCESS) {
                            binding.nsv.visibility = View.VISIBLE
                            Timber.tag("state").d(it.data.data!!.stateList.size.toString())
//                            binding.progress.visibility = View.GONE
                            binding.rvTrending.visibility = View.VISIBLE
                            /** setupRecyclerView */
                            setupFlexBoxRecyclerView()
                            trendingAdapter?.setNewData(it.data.data.stateList as MutableList<State>)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        stateListAdapter = StateListAdapter(context!!, this@SearchFragment)
        binding.rvRecent.apply {
            this.adapter = stateListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }
    }

    private fun setupFlexBoxRecyclerView() {
        trendingAdapter = TrendingAdapter(requireContext(), this@SearchFragment).also {
            binding.rvTrending.apply {
                this.adapter = it
                this.layoutManager = FlexboxLayoutManager(requireContext()).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.FLEX_START
                    alignItems = AlignItems.FLEX_START
                }
            }
        }
    }

    override fun onClick(state: State) {
        Toast.makeText(context, state.state, Toast.LENGTH_SHORT).show()
    }


    private fun setupSpeechRecognizer() {

        speechRecognizer.setRecognitionListener(object : RecognitionListener {

            //overrides
            override fun onReadyForSpeech(params: Bundle?) {
                onPerformSpeech = false
                Timber.tag("see1ss").d("$params")
            }

            override fun onBeginningOfSpeech() {
                Timber.tag("see2ss").d("onBeginningOfSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
//                Timber.tag("see3ss").d("$rmsdB")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Timber.tag("see4ss").d("$buffer")
            }

            override fun onEndOfSpeech() {
                onPerformSpeech = true
                Timber.tag("see5ss").d("onEndOfSpeech")
            }

            override fun onError(error: Int) {
                Timber.tag("see6ss").d("$error")
                if (onPerformSpeech && error == SpeechRecognizer.ERROR_NO_MATCH) return
            }

            override fun onResults(results: Bundle?) {
                Timber.tag("see7ss").d("${results?.size()}")
                Timber.tag("see7ssss").d(results.toString())

                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val scores = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
                if (matches != null) {
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Timber.tag("see8ss").d("$partialResults")
                val matches =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    // Handle partial matches
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Timber.tag("see9ss").d("$eventType : $params")
            }
        })
    }

    private fun requestVoicePermission() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            voicePermission.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TYPE_TEXT_STATE, typeText)
    }

    /** hold the state of current type Text */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            typeText = savedInstanceState.getString(TYPE_TEXT_STATE)
//            searchAdapter.clearData()
//            countryListViewModel.searchResult("117867598627360919374", 1, 10, typeText!!)
        }
    }


    private fun stopRecognition() {
        speechRecognizer.stopListening()
        speechRecognizer.destroy()
    }

    override fun onRefresh() {
        //countryListViewModel.getStateList()
//   countryListViewModel.searchResult("117867598627360919374", 1, 10, typeText!!)
//        binding.rvUserSearch.visibility = View.VISIBLE
    }

    fun handleOnBackPressed(): Boolean {
        //Do your job here

        return true
    }

}