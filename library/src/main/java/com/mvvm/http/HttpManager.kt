package com.mvvm.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.mvvm.MVVMLibrary
import com.mvvm.utils.L
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Yang Shihao
 */
object HttpManager {

    val COOKIE_CACHE = SetCookieCache()

    private val PERSISTENT_COOKIE_JAR =
        PersistentCookieJar(
            COOKIE_CACHE,
            SharedPrefsCookiePersistor(MVVMLibrary.context)
        )

    private val OK_HTTP_CLIENT = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .cookieJar(PERSISTENT_COOKIE_JAR)
        .addInterceptor(
            HttpLoggingInterceptor { message -> L.json("json__", message) }
                .setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
        )
        .build()

    val RETROFIT: Retrofit = Retrofit.Builder()
        .baseUrl(MVVMLibrary.CONFIG.httpHandler.getBaseUrl())
        .client(OK_HTTP_CLIENT)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    fun cancelAllRequest() {
        OK_HTTP_CLIENT.dispatcher.cancelAll()
        PERSISTENT_COOKIE_JAR.clear()
    }
}