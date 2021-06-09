package com.hao.easy.wan.fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.mvvm.webview.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.ArticleAdapter
import com.hao.easy.wan.adapter.ProjectTypePageAdapter
import com.hao.easy.wan.databinding.WanFragmentProjectBinding
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.viewmodel.ProjectViewModel
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.annotation.Inject
import com.mvvm.extensions.visibility
import com.mvvm.extensions.visible
import com.mvvm.ui.BaseListFragment
import com.mvvm.ui.UIParams

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class ProjectFragment :
    BaseListFragment<WanFragmentProjectBinding, Article, ProjectViewModel, ArticleAdapter>() {

    @Inject
    lateinit var typeAdapter: ProjectTypePageAdapter

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun initView() {
        super.initView()
        viewBinding {
            vpType.adapter = typeAdapter
            indicator.setViewPager(vpType)
            typeAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

            appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                baseRefreshLayout.isEnabled = verticalOffset == 0
            })
        }
    }

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            typeLiveData.observe(this@ProjectFragment) {
                typeAdapter.resetData(it)
                viewBinding {
                    line.visible()
                    indicator.visibility(it?.size ?: 0 > 1)
                }
            }
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> act {
                WebActivity.start(it, item.title, item.projectLink)
            }
            R.id.ivFavorite -> viewModel { collect(item, position) }
            else -> act {
                WebActivity.start(it, item.title, item.link)
            }
        }
    }
}