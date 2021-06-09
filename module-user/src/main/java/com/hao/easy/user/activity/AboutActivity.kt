package com.hao.easy.user.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.hao.easy.user.R
import com.hao.easy.user.adapter.AboutAdapter
import com.hao.easy.user.databinding.UserActivityAboutBinding
import com.hao.easy.user.model.Menu
import com.mvvm.adapter.listener.OnItemClickListener
import com.mvvm.adapter.listener.OnItemLongClickListener
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.annotation.Inject
import com.mvvm.extensions.init
import com.mvvm.ui.BaseActivity
import com.mvvm.utils.AppUtils
import com.mvvm.viewmodel.PlaceholderViewModel
import com.mvvm.webview.WebActivity

@AndroidEntryPoint(injectViewModel = false)
class AboutActivity : BaseActivity<UserActivityAboutBinding, PlaceholderViewModel>(),
    OnItemClickListener<Menu>,
    OnItemLongClickListener<Menu> {

    @Inject
    lateinit var adapter: AboutAdapter

    override fun initView() {
        title = getString(R.string.user_title_about)

        adapter.setOnItemClickListener(this)
        adapter.setOnItemLongClickListener(this)

        viewBinding {
            tvVersion.text = "V${AppUtils.getVersionName(this@AboutActivity)}"
            recyclerView.init(adapter)
        }
        adapter.resetData(createData())
    }

    override fun initData() {
    }

    override fun itemClicked(view: View, item: Menu, position: Int) {
        if (item.link == DOWNLOAD_LINK) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(DOWNLOAD_LINK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            WebActivity.start(this, item.webTitle, item.link)
        }
    }

    override fun itemLongClicked(view: View, item: Menu, position: Int) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, item.link))
        toast(R.string.user_copy_succeed)
    }

    companion object {
        private const val HOME = "https://haoshiy.github.io"
        private const val ANDROID_PROJECT_LINK = "https://github.com/haoshiy/kotlin_wanandroid"
        private const val WECHAT_PROJECT_LINK = "https://github.com/haoshiy/wechat_wanandroid"
        private const val DOWNLOAD_LINK = "http://fir.highstreet.top/kandroid"
        private const val THANKS = "https://wanandroid.com/blog/show/2"

        fun createData(): ArrayList<Menu> {
            val list = ArrayList<Menu>()
            list.add(Menu("个人主页", HOME, "个人主页", HOME))
            list.add(Menu("Android项目链接", ANDROID_PROJECT_LINK, "玩Android", ANDROID_PROJECT_LINK))
            list.add(Menu("小程序项目链接", WECHAT_PROJECT_LINK, "玩Android", WECHAT_PROJECT_LINK))
            list.add(Menu("Apk下载地址", DOWNLOAD_LINK, "", DOWNLOAD_LINK))
            list.add(Menu("Thanks", "鸿洋-玩Android开发Api", "鸿洋-玩Android开发Api", THANKS))
            return list
        }
    }
}
