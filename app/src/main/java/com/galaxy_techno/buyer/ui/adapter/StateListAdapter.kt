package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.State
import com.galaxy_techno.buyer.ui.delegate.StateDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.StateViewHolder

class StateListAdapter(context: Context, private val delegate: StateDelegate) :
    BaseRecyclerAdapter<StateViewHolder, State>(context) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<State> {
        val view = mLayoutInflator.inflate(R.layout.recent_item, parent, false)
        return StateViewHolder(view, delegate)
    }
}