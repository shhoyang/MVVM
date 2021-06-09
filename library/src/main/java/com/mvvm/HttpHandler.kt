package com.mvvm

import com.mvvm.http.HttpResponseModel

/**
 * @author Yang Shihao
 *
 * 网络相关
 */
interface HttpHandler {

    /**
     * base url
     */
    fun getBaseUrl(): String

    /**
     * 统一拦截数据，例如后台自定义的token失效
     * @return true 拦截  false默认
     */
    fun <T : HttpResponseModel<*>> handleResponse(t: T): Boolean

    /**
     * 统一拦截异常，例如401
     * @return true 拦截  false默认
     */
    fun handleError(t: Throwable): Boolean
}