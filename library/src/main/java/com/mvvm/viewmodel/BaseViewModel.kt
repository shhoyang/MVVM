package com.mvvm.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mvvm.MVVMLibrary
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val toastLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val loadingLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private var compositeDisposable: CompositeDisposable? = null

    open fun Disposable.add() {
        if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.apply {
            if (!isDisposed) {
                dispose()
            }
        }
        compositeDisposable = null
    }

    protected fun toast(text: String) {
        toastLiveData.value = text
    }

    protected fun toast(@StringRes resId: Int) {
        toastLiveData.value = MVVMLibrary.context.getString(resId)
    }

    protected fun showLoading() {
        loadingLiveData.value = true
    }

    protected fun hideLoading() {
        loadingLiveData.value = false
    }
}
