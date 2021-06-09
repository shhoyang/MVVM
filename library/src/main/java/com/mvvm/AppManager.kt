package com.mvvm

import android.app.Activity
import kotlin.system.exitProcess

class AppManager private constructor() {

    private val list = ArrayList<Activity>()

    fun pushActivity(activity: Activity) {
        list.add(activity)
    }

    fun popActivity(activity: Activity) {
        process { it == activity }
    }

    fun finishActivity(cls: Class<out Activity>) {
        process { it.javaClass == cls }
    }

    /**
     * 移除指定类名的Activity之外的所有Activity
     */
    fun finishOtherActivity(cls: Class<out Activity>) {
        process { it.javaClass != cls }
    }

    /**
     * 移除指定类名的Activity之外的所有Activity
     */
    fun finishOtherActivity(activity: Activity) {
        process { it != activity }
    }

    fun exit() {
        process { true }
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }

    private fun process(b: (Activity) -> Boolean) {
        if (list.isEmpty()) {
            return
        }
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (b(next)) {
                if (!next.isFinishing) {
                    next.finish()
                }
                iterator.remove()
            }
        }
    }

    companion object {
        private var instance: AppManager? = null

        @Synchronized
        fun instance(): AppManager {
            if (instance == null) {
                instance = AppManager()
            }
            return instance!!
        }
    }
}