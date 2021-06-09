package com.mvvm.webview.view

/**
 * @author Yang Shihao
 */
interface WebViewLoadListener {

    fun pageFinished()

    fun pageLoadError()

    fun progressChanged(newProgress: Int)
}