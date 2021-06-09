package com.mvvm.http

/**
 * @author Yang Shihao
 */
data class ResponseException(
    val errorCode: String,
    val errorMsg: String,
    val detailMsg: String
)