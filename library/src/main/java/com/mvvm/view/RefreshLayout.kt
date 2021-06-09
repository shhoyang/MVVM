package com.mvvm.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

open class RefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun stopRefresh() {
        isRefreshing = false
    }

    fun stopLoadMore(success: Boolean,noMoreData: Boolean) {

    }

    fun resetNoMoreData(){

    }
}