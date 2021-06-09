package com.mvvm.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.mvvm.constant.DataListResult
import com.mvvm.viewmodel.datasource.DataSourceFactory
import com.mvvm.viewmodel.datasource.PagedDataLoader

abstract class BaseListViewModel<T> : BaseViewModel(), PagedDataLoader<T> {

    private val pageSize: Int by lazy { pageSize() }

    private val dataSourceFactory: DataSourceFactory<T> by lazy { DataSourceFactory(this) }

    val loadLiveData: LiveData<PagedList<T>> by lazy {
        LivePagedListBuilder(
            dataSourceFactory,
            pageSize
        ).build()
    }

    val refreshLiveData: MutableLiveData<DataListResult> by lazy { MutableLiveData<DataListResult>() }

    val loadMoreLiveData: MutableLiveData<DataListResult> by lazy { MutableLiveData<DataListResult>() }

    val notifyItemLiveData: MutableLiveData<Pair<Int, Any?>> by lazy { MutableLiveData<Pair<Int, Any?>>() }

    val removeItemLiveData: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }


    open fun pageSize(): Int {
        return 10
    }

    fun refresh() {
        dataSourceFactory.invalidate()
    }

    @CallSuper
    override fun refresh(callback: PageKeyedDataSource.LoadInitialCallback<Int, T>) {
        loadData(1) {
            val result = when {
                it == null -> DataListResult.FAILED
                it.isEmpty() -> DataListResult.NO_DATA
                it.size < pageSize() -> {
                    callback.onResult(it, null, null)
                    DataListResult.NO_MORE
                }
                else -> {
                    callback.onResult(it, null, 2)
                    DataListResult.SUCCEED
                }
            }
            refreshLiveData.postValue(result)
        }
    }

    @CallSuper
    override fun loadMore(key: Int, callback: PageKeyedDataSource.LoadCallback<Int, T>) {
        loadData(key) {
            val result = when {
                it == null -> DataListResult.FAILED
                it.size < pageSize() -> {
                    callback.onResult(it, null)
                    DataListResult.NO_MORE
                }
                else -> {
                    callback.onResult(it, key + 1)
                    DataListResult.SUCCEED
                }
            }
            loadMoreLiveData.postValue(result)
        }
    }

    fun notifyItem(position: Int, payload: Any? = null) {
        notifyItemLiveData.value = Pair(position, payload)
    }

    fun removeItem(position: Int) {
        removeItemLiveData.value = position
    }

    abstract fun loadData(page: Int, onResponse: (ArrayList<T>?) -> Unit)
}
