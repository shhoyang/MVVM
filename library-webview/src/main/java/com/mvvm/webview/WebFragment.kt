package com.mvvm.webview

import android.os.Bundle
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.viewmodel.PlaceholderViewModel
import com.mvvm.webview.databinding.FragmentWebBinding
import com.mvvm.webview.view.WebViewLoadListener

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint(injectViewModel = false)
class WebFragment : BaseWebFragment<FragmentWebBinding, PlaceholderViewModel>(),
    WebViewLoadListener {

    override fun initView() {
        super.initView()
        load(arguments?.getString(URL,"") ?: "")
    }

    companion object {
        fun instance(url: String): WebFragment {
            val fragment = WebFragment()
            val bundle = Bundle()
            bundle.putString(URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }
}