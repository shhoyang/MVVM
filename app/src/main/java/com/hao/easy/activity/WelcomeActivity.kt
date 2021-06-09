package com.hao.easy.activity

import com.hao.easy.databinding.AppActivityWelcomeBinding
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.ui.BaseActivity
import com.mvvm.viewmodel.PlaceholderViewModel

@AndroidEntryPoint(injectViewModel = false)
class WelcomeActivity : BaseActivity<AppActivityWelcomeBinding, PlaceholderViewModel>() {

    override fun initView() {
        toA(MainActivity::class.java, true)
    }

    override fun initData() {
    }
}
