package com.mvvm

/**
 * @author Yang Shihao
 */
interface LoginHandler {

    /**
     * 是否登录
     */
    fun isLogin(): Boolean

    /**
     * 去登录
     */
    fun login()
}