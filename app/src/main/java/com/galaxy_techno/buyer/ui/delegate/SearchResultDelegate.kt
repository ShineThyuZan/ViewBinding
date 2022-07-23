package com.galaxy_techno.buyer.ui.delegate

import com.galaxy_techno.buyer.model.dto.SearchProductResponse

interface SearchResultDelegate {
    fun onClickSearchProductResult(result : SearchProductResponse.SearchResult)
}