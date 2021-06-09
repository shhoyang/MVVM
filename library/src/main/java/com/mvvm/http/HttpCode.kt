package com.mvvm.http

/**
 * @author Yang Shihao
 */
enum class HttpCode(val errorCode: String, val errorMsg: String) {
    UNKNOWN_HOST_EXCEPTION("1001", "UnknownHostException"),
    CONNECT_EXCEPTION("1002", "ConnectException"),
    SOCKET_EXCEPTION("1003", "SocketException"),
    SOCKET_TIMEOUT_EXCEPTION("1004", "SocketTimeoutException"),
    SSL_HANDSHAKE_EXCEPTION("1005", "SSLHandshakeException"),
    JSON_EXCEPTION("1006", "JSONException"),
    UNKNOWN("1007", "未知异常");
}