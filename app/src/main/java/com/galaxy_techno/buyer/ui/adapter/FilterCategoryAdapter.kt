package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Apps
import com.galaxy_techno.buyer.ui.delegate.DiscountProductDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.FilterCategoryViewHolder

class FilterCategoryAdapter(
    context: Context,
    val mDelegate: DiscountProductDelegate
) : BaseRecyclerAdapter<FilterCategoryViewHolder, Apps>(context) {
    var mDataset = arrayOf("", "")


    companion object {
        var checkedPosition = 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Apps> {
        val view = mLayoutInflator.inflate(R.layout.trending_item, parent, false)
        return FilterCategoryViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Apps>, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            holder.itemView.setBackgroundResource(R.drawable.color_gradient_yellow)
            holder.itemView.findViewById<TextView>(R.id.tv)
                .setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))

            // 0 1
            if (checkedPosition != position) {
                notifyItemChanged(checkedPosition)
                checkedPosition = position
            }
            mDelegate.onProductOnClick(mData!![position])
        }
    }
}