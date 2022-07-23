package com.galaxy_techno.buyer.presentation.second_lvl_dest.search

import androidx.lifecycle.ViewModel
import com.galaxy_techno.buyer.domain.AppRepository
import com.galaxy_techno.buyer.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository
) : ViewModel() {
    //todo get search result from server
    // make mutable state holder and immutable state value
}