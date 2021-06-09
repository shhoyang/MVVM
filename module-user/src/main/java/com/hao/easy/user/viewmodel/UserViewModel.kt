package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.user.repository.Api
import com.mvvm.http.subscribeBy
import com.mvvm.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
class UserViewModel : BaseViewModel() {

    val logoutLiveData = MutableLiveData<String?>()

    fun logout() {
        Api.logout().subscribeBy({
            logoutLiveData.value = null
        }).add()
    }
}