package com.galaxy_techno.buyer.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.galaxy_techno.buyer.R
import com.galaxy_techno.buyer.model.dto.Movie
import com.galaxy_techno.buyer.ui.delegate.MovieDelegate
import com.galaxy_techno.buyer.ui.viewholder.BaseViewHolder
import com.galaxy_techno.buyer.ui.viewholder.MovieViewHolder

class MovieAdapter(context: Context, val delegate: MovieDelegate) :
    BaseRecyclerAdapter<MovieViewHolder, Movie>(context) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var lastPage = false
    private var isLoading = false
    var proBar: ProgressBar? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Movie> {


        return if (viewType == VIEW_TYPE_ITEM) {
            val v = mLayoutInflator.inflate(R.layout.single_product_viewpod, parent, false)
            MovieViewHolder(v, delegate)
        } else {
            val view = mLayoutInflator.inflate(R.layout.view_holder_loadmore, parent, false)
            LoadMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Movie>, position: Int) {
        if (holder is MovieViewHolder) {
            super.onBindViewHolder(holder, position)
        } else {

            if (lastPage || position == 0) {

                proBar = holder.itemView.findViewById<View>(R.id.progressBar) as ProgressBar
                proBar!!.visibility = View.GONE
            } else {
                proBar!!.visibility = View.VISIBLE

            }
        }
        if (position == mData!!.size - 10 && !lastPage && !isLoading) {
            delegate.loadMore()
        }
    }

    fun isLastPage(lastPage: Boolean) {
        this.lastPage = lastPage
        notifyDataSetChanged()
    }

    fun isLoading(isLoading: Boolean) {
        this.isLoading = isLoading
//        Handler().postDelayed({ this.isLoading = false }, 5000)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData!!.size == position) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (mData == null && mData!!.size == 0) 0 else mData!!.size + 1
    }

    class LoadMoreViewHolder(v: View) : BaseViewHolder<Movie>(v) {
        override fun setData(data: Movie) {
        }

        override fun onClick(v: View?) {
        }
    }
}