package com.hao.easy.base

import android.app.Application
import com.hao.easy.base.constant.Constant
import com.mvvm.*
import com.mvvm.extensions.notNullSingleValue
import com.mvvm.http.HttpResponseModel

/**
 * @author Yang Shihao
 */

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MVVMLibraryConfig.Builder(this)
            .setToolbarLayoutTheme(R.style.MyToolbarLayout)
            .setHttpHandler(MyHttpHandler())
            .setLoginHandler(MyLoginHandler())
            .apply()
    }

    class MyHttpHandler : HttpHandler {

        override fun getBaseUrl(): String {
            return Constant.BASE_URL
        }

        override fun <T : HttpResponseModel<*>> handleResponse(t: T): Boolean {
            return when (t.getCode()) {
                // 未登录
                "-1001" -> {
                    Router.startLogin()
                    true
                }
                else -> false
            }
        }

        override fun handleError(t: Throwable): Boolean {
            return false
        }
    }

    class MyLoginHandler : LoginHandler {
        override fun isLogin(): Boolean {
            return Config.isLogin
        }

        override fun login() {
            Router.startLogin()
        }
    }

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }
}