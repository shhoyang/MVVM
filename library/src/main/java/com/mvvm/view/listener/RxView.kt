package com.mvvm.view.listener

import android.view.View
import android.widget.EditText

/**
 * @author Yang Shihao
 */
object RxView {

    private const val CLICK_INTERVAL = 1000

    fun textChanges(vararg et: EditText, block: () -> Unit) {
        et.forEach {
            it.addTextChangedListener(object : SimpleTextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    block()
                }
            })
        }
    }

    fun click(view: View, block: () -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= CLICK_INTERVAL) {
                pre = c
                block()
            }
        }
    }

    fun click(view: View, interval: Int, block: () -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= interval) {
                pre = c
                block()
            }
        }
    }

    fun <T> click(view: View, t: T, block: (T) -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= CLICK_INTERVAL) {
                pre = c
                block(t)
            }
        }
    }

    fun <T> click(view: View, interval: Int, t: T, block: (T) -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= interval) {
                pre = c
                block(t)
            }
        }
    }

    fun <T1, T2> click(view: View, t1: T1, t2: T2, block: (T1, T2) -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= CLICK_INTERVAL) {
                pre = c
                block(t1, t2)
            }
        }
    }

    fun <T1, T2> click(view: View, interval: Int, t1: T1, t2: T2, block: (T1, T2) -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= interval) {
                pre = c
                block(t1, t2)
            }
        }
    }

    fun <T1, T2, T3> click(view: View, t1: T1, t2: T2, t3: T3, block: (T1, T2, T3) -> Unit) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= CLICK_INTERVAL) {
                pre = c
                block(t1, t2, t3)
            }
        }
    }

    fun <T1, T2, T3> click(
        view: View,
        interval: Int,
        t1: T1,
        t2: T2,
        t3: T3,
        block: (T1, T2, T3) -> Unit
    ) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= interval) {
                pre = c
                block(t1, t2, t3)
            }
        }
    }

    fun click(view: View, listener: View.OnClickListener) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= CLICK_INTERVAL) {
                pre = c
                listener.onClick(view)
            }
        }
    }

    fun click(view: View, interval: Int, listener: View.OnClickListener) {
        var pre = 0L
        view.setOnClickListener {
            val c = System.currentTimeMillis()
            if (c - pre >= interval) {
                pre = c
                listener.onClick(view)
            }
        }
    }
}