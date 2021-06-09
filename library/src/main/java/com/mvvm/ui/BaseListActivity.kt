package com.mvvm.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mvvm.R
import com.mvvm.adapter.BasePagedAdapter
import com.mvvm.adapter.PagedAdapterItem
import com.mvvm.adapter.listener.OnItemClickListener
import com.mvvm.annotation.Base
import com.mvvm.annotation.Inject
import com.mvvm.constant.DataListResult
import com.mvvm.view.EmptyView
import com.mvvm.view.RefreshLayout
import com.mvvm.viewmodel.BaseListViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseListActivity<VB : ViewBinding, D : PagedAdapterItem, VM : BaseListViewModel<D>, A : BasePagedAdapter<*, D>> :
    BaseActivity<VB, VM>(),
    OnItemClickListener<D> {

    private var refreshLayout: RefreshLayout? = null
    private var emptyView: EmptyView? = null

    @Inject
    lateinit var adapter: A

    @CallSuper
    override fun initView() {
        val recyclerView: RecyclerView = f(R.id.baseRecyclerView)!!
        adapter.setOnItemClickListener(this)
        recyclerView.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(this@BaseListActivity)
            adapter = this@BaseListActivity.adapter
        }
        this.refreshLayout = f(R.id.baseRefreshLayout)
        this.emptyView = f(R.id.baseEmptyView)
        this.refreshLayout?.setOnRefreshListener {
            refreshLayout?.resetNoMoreData()
            viewModel { refresh() }
        }
    }

    @CallSuper
    override fun initData() {
        viewModel {
            loadLiveData.observe(this@BaseListActivity) {
                adapter.submitList(it)
            }
            refreshLiveData.observe(this@BaseListActivity) {
                refreshFinished(it)
            }
            loadMoreLiveData.observe(this@BaseListActivity) {
                loadMoreFinished(it)
            }
            if (uiParams.hasItemChange) {
                notifyItemLiveData.observe(this@BaseListActivity) {
                    adapter.notifyItemChanged(it.first, it.second)
                }
            }
            if (uiParams.hasItemRemove) {
                removeItemLiveData.observe(this@BaseListActivity) {
                    adapter.notifyItemRemoved(it)
                }
            }
        }
    }

    override fun itemClicked(view: View, item: D, position: Int) {

    }

    @CallSuper
    open fun refreshFinished(result: DataListResult) {
        refreshLayout?.stopRefresh()
        emptyView?.apply {
            when (result) {
                DataListResult.SUCCEED -> dismiss()
                DataListResult.FAILED -> loadFailed()
                DataListResult.NO_DATA -> noData()
                DataListResult.NO_MORE -> dismiss()
            }
        }
    }

    open fun loadMoreFinished(result: DataListResult) {
        refreshLayout?.apply {
            when (result) {
                DataListResult.SUCCEED -> stopLoadMore(success = true, noMoreData = false)
                DataListResult.FAILED -> stopLoadMore(success = false, noMoreData = false)
                DataListResult.NO_MORE -> stopLoadMore(success = true, noMoreData = true)
            }
        }
    }
}