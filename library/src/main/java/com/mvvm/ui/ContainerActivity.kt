package com.mvvm.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mvvm.R
import com.mvvm.annotation.Base
import com.mvvm.viewmodel.PlaceholderViewModel

@Base
abstract class ContainerActivity<VB : ViewBinding> : BaseActivity<VB, PlaceholderViewModel>() {

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, getFragment())
            .commit()
    }

    override fun initData() {

    }

    abstract fun getFragment(): Fragment
}
