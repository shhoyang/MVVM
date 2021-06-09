package com.mvvm.webview.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.*


class ProgressWebView : WebView {

    private var address = "-1"
    private var webViewLoadListener: WebViewLoadListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        val settings = settings
        settings.setSupportZoom(true)
        settings.setSupportMultipleWindows(true)
        settings.setAppCacheEnabled(true)
        settings.setGeolocationEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.allowFileAccess = true
        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.savePassword = true
        settings.saveFormData = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        settings.loadsImagesAutomatically = true
        settings.displayZoomControls = false
        settings.defaultTextEncodingName = "utf-8"
        webViewClient = client
        webChromeClient = chromeClient
    }

    private val client = object : WebViewClient() {
        override fun onPageFinished(webView: WebView, url: String) {
            super.onPageFinished(webView, url)
            webViewLoadListener?.pageFinished()
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }


        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            if (request.isForMainFrame) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                webViewLoadListener?.pageLoadError()
            }
        }
    }

    private val chromeClient = object : WebChromeClient() {
        override fun onProgressChanged(webView: WebView, newProgress: Int) {
            super.onProgressChanged(webView, newProgress)
            webViewLoadListener?.apply {
                if (newProgress >= 100) {
                    pageFinished()
                }
                progressChanged(newProgress)
            }
        }
    }

    override fun destroy() {
        webViewLoadListener = null
        super.destroy()
    }

    fun setWebViewLoadListener(pageLoadListener: WebViewLoadListener?) {
        this.webViewLoadListener = pageLoadListener
    }


    fun doLoadUrl(url: String, userSonic: Boolean = false) {
        if (address == url) {
            reload()
            return
        }
        address = url
        loadUrl(address)
    }
}
