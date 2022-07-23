package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.model.dto.ItemDTO
import com.galaxy_techno.buyer.ui.delegate.CategoryNameDelegate
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.MainCategoryNameViewHolder

class MainCategoryProductsAdapter(
    mContext: Context,
    val delegate: CategoryNameDelegate,
    val subDelegate: DiscountProductDelegate
) : BaseRecyclerAdapter<MainCategoryNameViewHolder, ItemDTO.App>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            BaseViewHolder<ItemDTO.App> {
        val view = mLayoutInflator.inflate(R.layout.category_name_and_rv_view, parent, false)
        return MainCategoryNameViewHolder(view, delegate)
    }
        // create itemAdapter with same viewHolder
    override fun onBindViewHolder(holder: BaseViewHolder<ItemDTO.App>, position: Int) {
        super.onBindViewHolder(holder, position)
        val mAdapter = ProductAdapter(holder.itemView.context, subDelegate)
        val rvView: RecyclerView?
        rvView = holder.itemView.findViewById<View>(R.id.rvProducts) as RecyclerView
        rvView.adapter = mAdapter
        rvView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        rvView.setHasFixedSize(true)
        mAdapter.setNewData(items[position].apps as MutableList<Apps>)
    }
}

