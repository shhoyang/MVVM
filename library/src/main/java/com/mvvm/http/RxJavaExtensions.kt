package com.mvvm.http

import com.google.gson.JsonParseException
import com.mvvm.MVVMLibrary
import com.mvvm.utils.T
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * 切换线程io-main，只回调data部分
 * @param onResponse 成功回调
 * @param onFailure 失败回调
 * @param toastWhenSucceed 成功的时候是否Toast
 * @param toastWhenFailed 失败的时候是否Toast
 */
fun <D, T : HttpResponseModel<D>> Observable<T>.subscribeBy(
    onResponse: (D?) -> Unit,
    onFailure: (ResponseException) -> Unit = {},
    toastWhenSucceed: Boolean = false,
    toastWhenFailed: Boolean = false
): Disposable = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({
        // 没有消费
        if (!MVVMLibrary.CONFIG.httpHandler.handleResponse(it)) {
            if (it.isSucceed()) {
                if (toastWhenSucceed) {
                    toast(it.getMessage())
                }
                onResponse(it.getData())
            } else {
                if (toastWhenFailed) {
                    toast(it.getMessage())
                }
                onFailure(ResponseException(it.getCode(), it.getMessage(), it.getMessage()))
            }
        }
    }, {
        processError(it, toastWhenFailed, onFailure)
    })

/**
 * 切换线程io-main，回调整个响应
 * @param onResponse 成功回调
 * @param onFailure 失败回调
 * @param toastWhenFailed 失败的时候是否Toast
 */
fun <T> Observable<T>.subscribeBy2(
    onResponse: (T?) -> Unit,
    onFailure: (ResponseException) -> Unit = {},
    toastWhenFailed: Boolean = false
): Disposable = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({
        onResponse(it)
    }, {
        processError(it, toastWhenFailed, onFailure)
    })

/**
 * 当前线程，一般是子线程，所以不toast。只回调data部分
 * @param onResponse 成功回调
 * @param onFailure 失败回调
 */
fun <D, T : HttpResponseModel<D>> Observable<T>.subscribeBy3(
    onResponse: (D?) -> Unit,
    onFailure: (ResponseException) -> Unit = {},
): Disposable = subscribe({
    // 没有消费
    if (!MVVMLibrary.CONFIG.httpHandler.handleResponse(it)) {
        if (it.isSucceed()) {
            onResponse(it.getData())
        } else {
            onFailure(ResponseException(it.getCode(), it.getMessage(), it.getMessage()))
        }
    }
}, {
    processError(it, false, onFailure)
})

/**
 * 当前线程，一般是子线程，所以不toast。回调整个响应
 * @param onResponse 成功回调
 * @param onFailure 失败回调
 */
fun <T> Observable<T>.subscribeBy4(
    onResponse: (T?) -> Unit,
    onFailure: (ResponseException) -> Unit = {},
): Disposable =
    subscribe({
        onResponse(it)
    }, {
        processError(it, false, onFailure)
    })

fun processError(throwable: Throwable, toast: Boolean, onFailure: (ResponseException) -> Unit) {
    throwable.printStackTrace()

    if (MVVMLibrary.CONFIG.httpHandler.handleError(throwable)) {
        return
    }

    val responseException: ResponseException = when (throwable) {
        is HttpException -> {
            var code = throwable.code().toString()
            ResponseException(code, code, throwable.message())
        }
        is CustomException -> {
            // throwable.message不会为空
            val msg = throwable.message ?: HttpError.UNKNOWN.errorMsg
            ResponseException(HttpError.UNKNOWN.errorCode, msg, msg)
        }
        else -> {
            val httpError = when (throwable) {
                is UnknownHostException -> HttpError.UNKNOWN_HOST_EXCEPTION
                is ConnectException -> HttpError.CONNECT_EXCEPTION
                is SocketException -> HttpError.SOCKET_EXCEPTION
                is SocketTimeoutException -> HttpError.SOCKET_TIMEOUT_EXCEPTION
                is SSLHandshakeException -> HttpError.SSL_HANDSHAKE_EXCEPTION
                is JSONException -> HttpError.JSON_EXCEPTION
                is JsonParseException -> HttpError.JSON_EXCEPTION
                else -> HttpError.UNKNOWN
            }
            return with(httpError) {
                ResponseException(errorCode, errorMsg, throwable.message ?: errorMsg)
            }
        }
    }

    if (toast) {
        toast(responseException.errorMsg)
    }

    onFailure(responseException)
}

fun toast(msg: String) {
    T.short(MVVMLibrary.context, msg)
}