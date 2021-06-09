package com.mvvm.adapter.listener

import android.view.View

/**
 * @author Yang Shihao
 */

interface OnItemLongClickListener<T> {
    fun itemLongClicked(view: View, item: T, position: Int)
}