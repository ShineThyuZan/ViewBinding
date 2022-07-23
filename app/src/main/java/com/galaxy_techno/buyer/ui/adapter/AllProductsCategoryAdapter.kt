package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.CountryVos
import com.galaxy_techno.buyer.ui.delegate.CountrySelectedDelegate
import com.galaxy_techno.buyer.ui.viewholder.AllProductCategoryViewHolder
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder


class AllProductsCategoryAdapter (context: Context, private val delegate: CountrySelectedDelegate) :
    BaseRecyclerAdapter<AllProductCategoryViewHolder, CountryVos>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryVos> {
        val view = mLayoutInflator.inflate(R.layout.all_category_view_pod, parent, false)
        return AllProductCategoryViewHolder(view, delegate)
    }
}