package com.galaxy_techno.buyer.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.model.dto.Movie
import com.galaxy_techno.buyer.model.dto.ResponseMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {


    private lateinit var _movies: Flow<PagingData<Movie>>
    val movies: Flow<PagingData<Movie>> get() = _movies

    private val _movieList = MutableLiveData<UiState<ResponseMovies>>()
    val movieList : LiveData<UiState<ResponseMovies>> get() = _movieList

    fun getPagerMovies() {

        viewModelScope.launch {

            appRepository.getPagingMovies().collect {
                _movies = appRepository.getPagingMovies()
            }

        }
    }

    fun getMovie(key : String , page : Int ) {
        viewModelScope.launch {
            _movieList.value = (UiState.Loading())
            appRepository.getMovies(key , page).collect {
                _movieList.postValue(it)
            }
        }
    }

}