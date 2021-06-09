package com.mvvm.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mvvm.R
import com.mvvm.adapter.BaseNormalAdapter
import com.mvvm.adapter.listener.OnItemClickListener
import com.mvvm.annotation.Base
import com.mvvm.annotation.Inject
import com.mvvm.view.EmptyView
import com.mvvm.view.RefreshLayout
import com.mvvm.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseNormalListActivity<VB : ViewBinding, D, VM : BaseViewModel, A : BaseNormalAdapter<*, D>> :
    BaseActivity<VB, VM>(),
    OnItemClickListener<D> {

    private var refreshLayout: RefreshLayout? = null
    private var emptyView: EmptyView? = null

    @Inject
    lateinit var adapter: A

    @CallSuper
    override fun initView() {
        this.refreshLayout = f(R.id.baseRefreshLayout)
        this.emptyView = f(R.id.baseEmptyView)
        val recyclerView: RecyclerView = f(R.id.baseRecyclerView)!!
        registerDataObserver()
        adapter.setOnItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        this.refreshLayout?.setOnRefreshListener {
            onRefresh()
        }
    }

    private fun registerDataObserver() {
        if (null == emptyView) {
            return
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (adapter.data.isEmpty()) {
                    emptyView?.noData()
                } else {
                    emptyView?.dismiss()
                }
            }
        })
    }

    open fun onRefresh() {

    }

    override fun itemClicked(view: View, item: D, position: Int) {

    }
}