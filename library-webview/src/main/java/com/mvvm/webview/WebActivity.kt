package com.mvvm.webview

import android.content.Context
import android.content.Intent
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.viewmodel.PlaceholderViewModel
import com.mvvm.webview.databinding.ActivityWebBinding
import com.mvvm.webview.view.WebViewLoadListener

/**
 * @author Yang Shihao
 * @Date 3/6/21
 */
@AndroidEntryPoint(injectViewModel = false)
class WebActivity : BaseWebActivity<ActivityWebBinding, PlaceholderViewModel>(),
    WebViewLoadListener {

    override fun initView() {
        super.initView()
        title = intent.getStringExtra(TITLE)
        load(intent.getStringExtra(URL) ?: "")
    }

    companion object {
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }
}