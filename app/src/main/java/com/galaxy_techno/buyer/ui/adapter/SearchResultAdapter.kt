package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.SearchProductResponse
import com.galaxy_techno.buyer.ui.delegate.SearchResultDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.SearchResultViewHolder

class SearchResultAdapter(
    context: Context,
    private val delegate: SearchResultDelegate
) : BaseRecyclerAdapter<SearchResultViewHolder, SearchProductResponse.SearchResult>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SearchProductResponse.SearchResult> {
        val view = mLayoutInflator.inflate(R.layout.layout_search_result_item, parent, false)
        return SearchResultViewHolder(view, delegate)
    }
}