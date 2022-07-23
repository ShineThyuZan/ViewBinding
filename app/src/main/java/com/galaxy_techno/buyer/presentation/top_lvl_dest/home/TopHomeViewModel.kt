package com.galaxy_techno.buyer.presentation.top_lvl_dest.home

import androidx.lifecycle.ViewModel
import com.galaxy_techno.buyer.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopHomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel()