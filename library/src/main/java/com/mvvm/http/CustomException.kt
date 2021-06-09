package com.mvvm.http

import androidx.annotation.StringRes
import com.mvvm.MVVMLibrary
import java.lang.Exception

/**
 * @author Yang Shihao
 */
class CustomException(message: String) : Exception(message) {

    constructor(@StringRes resId: Int) : this(MVVMLibrary.context.getString(resId))
}