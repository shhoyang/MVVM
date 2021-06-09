package com.mvvm.ui

import androidx.fragment.app.Fragment

/**
 * @author Yang Shihao
 */
interface FragmentCreator {
    fun createFragment(): Fragment
}