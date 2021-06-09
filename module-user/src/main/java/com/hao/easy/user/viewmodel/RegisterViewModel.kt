package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.user.repository.Api
import com.mvvm.http.subscribeBy
import com.mvvm.model.ParamsBuilder
import com.mvvm.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
class RegisterViewModel : BaseViewModel() {

    val registerLiveData = MutableLiveData<String?>()

    fun register(username: String, password: String) {
        val params = ParamsBuilder()
            .put("username", username)
            .put("password", password)
            .put("repassword", password)
            .build()
        Api.register(params).subscribeBy({
            registerLiveData.value = null
        }).add()
    }
}