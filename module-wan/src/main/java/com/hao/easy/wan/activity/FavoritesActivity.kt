package com.hao.easy.wan.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.FavoritesAdapter
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.viewmodel.FavoritesViewModel
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.databinding.ActivityBaseListBinding
import com.mvvm.ui.BaseListActivity
import com.mvvm.view.dialog.ConfirmDialog
import com.mvvm.view.dialog.ConfirmDialogListener
import com.mvvm.webview.WebActivity

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
@Route(path = "/wan/FavoritesActivity")
class FavoritesActivity :
    BaseListActivity<ActivityBaseListBinding, Article, FavoritesViewModel, FavoritesAdapter>(){

    override fun initView() {
        title = getString(R.string.wan_title_fav)
        super.initView()
    }

    override fun initData() {
        super.initData()
        viewModel {
            deleteResult.observe(this@FavoritesActivity) {
                toast(R.string.wan_cancel_fav)
            }
        }

    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFavorite) {
            ConfirmDialog.Builder(this)
                .setMessage(getString(R.string.wan_confirm_cancel_fav))
                .setListener(object : ConfirmDialogListener {
                    override fun confirm() {
                        viewModel { cancelCollect(item, position) }
                    }

                    override fun cancel() {
                    }

                }).build().show()
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}