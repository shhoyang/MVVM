package com.hao.easy.user.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.user.databinding.UserActivityLoginBinding
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.ui.BaseActivity
import com.mvvm.viewmodel.PlaceholderViewModel

@AndroidEntryPoint(injectViewModel = false)
@Route(path = "/user/LoginActivity")
class LoginActivity : BaseActivity<UserActivityLoginBinding, PlaceholderViewModel>() {

    override fun initView() {
    }

    override fun initData() {
    }
}
