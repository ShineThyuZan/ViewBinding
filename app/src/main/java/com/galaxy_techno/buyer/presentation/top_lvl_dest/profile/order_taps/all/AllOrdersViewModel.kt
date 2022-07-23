package com.galaxy_techno.buyer.presentation.top_lvl_dest.profile.order_taps.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.galaxy_techno.buyer.di.Qualifier
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @Qualifier.Io private val io: CoroutineDispatcher
    ) : ViewModel() {

    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> get() = _movies

    init {
        getPagerMovies()
    }
    private fun getPagerMovies() {

        viewModelScope.launch(io) {
            val data = appRepository.getPagingMovies()
            data.collect {
                _movies.postValue(it)
            }
        }
    }

}