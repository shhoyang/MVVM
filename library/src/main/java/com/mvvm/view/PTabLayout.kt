package com.mvvm.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout

/**
 * @author Yang Shihao
 */
class PTabLayout : TabLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        tabMode = MODE_FIXED
    }
}