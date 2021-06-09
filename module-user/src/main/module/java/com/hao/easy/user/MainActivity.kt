package com.hao.easy.user

import android.content.Intent
import com.hao.easy.user.fragment.UserFragment
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.databinding.ActivityContainerBinding
import com.mvvm.ui.ContainerActivity
import com.mvvm.ui.UIParams

@AndroidEntryPoint(injectViewModel = false)
class MainActivity : ContainerActivity<ActivityContainerBinding>() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.isTransparentStatusBar = true
    }

    override fun getFragment() = UserFragment()
}

