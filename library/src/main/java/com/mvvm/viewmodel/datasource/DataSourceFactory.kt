package com.mvvm.viewmodel.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class DataSourceFactory<T>(private var dataLoader: PagedDataLoader<T>) :
    DataSource.Factory<Int, T>() {

    private val sourceLiveData = MutableLiveData<PagedDataSource<T>>()

    override fun create(): DataSource<Int, T> {
       return PagedDataSource(dataLoader).apply {
           sourceLiveData.postValue(this)
       }
    }

    fun invalidate() {
        sourceLiveData.value?.invalidate()
    }
}
