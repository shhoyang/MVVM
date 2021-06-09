package com.mvvm.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mvvm.utils.DisplayUtils

/**
 * @author Yang Shihao
 *
 * 状态栏等高
 */
class StatusBarPlaceholderView : View {

    private var statusBarHeight = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        statusBarHeight = DisplayUtils.getStatusBarHeight(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY)
        )
    }
}