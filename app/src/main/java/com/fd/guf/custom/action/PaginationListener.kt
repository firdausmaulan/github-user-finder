package com.fd.guf.custom.action

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fd.guf.utils.Constants


abstract class PaginationListener(private val rvLayoutManager: RecyclerView.LayoutManager?) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = rvLayoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= Constants.PER_PAGE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLoading: Boolean
}