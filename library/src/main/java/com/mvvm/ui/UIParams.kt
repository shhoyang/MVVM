package com.mvvm.ui

/**
 * @author Yang Shihao
 */
data class UIParams (
    // 从状态栏布局
    var isTransparentStatusBar:Boolean = false,
    var isLazy: Boolean = false,
    var hasItemChange: Boolean = true,
    var hasItemRemove: Boolean = true
)