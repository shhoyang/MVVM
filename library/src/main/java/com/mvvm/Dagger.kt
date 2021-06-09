package com.mvvm

import android.app.Activity
import androidx.fragment.app.Fragment
import com.mvvm.annotation.DaggerConstant

/**
 * @author Yang Shihao
 */
object Dagger {

    fun inject(activity: Activity) {
        invoke(activity)
    }

    fun inject(fragment: Fragment) {
        invoke(fragment)
    }

    private fun invoke(any: Any) {
        val clazz = Class.forName(any.javaClass.name + DaggerConstant.SUFFIX)
        val method = clazz.getMethod(DaggerConstant.STATIC_METHOD_NAME, any.javaClass)
        method.invoke(null, any)
    }
}