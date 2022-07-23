package com.galaxy_techno.buyer.ui.delegate

import com.galaxy_techno.buyer.model.dto.Movie

interface MovieDelegate {
    fun onClickedMovieDelegate(notification: Movie)
    fun loadMore()
}