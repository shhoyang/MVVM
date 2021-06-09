package com.mvvm

import android.app.Application
import com.mvvm.extensions.notNullSingleValue

/**
 * @author Yang Shihao
 */

object MVVMLibrary {

    var context by notNullSingleValue<Application>()
    var CONFIG by notNullSingleValue<MVVMLibraryConfig>()

    fun init(libraryConfig: MVVMLibraryConfig) {
        this.CONFIG = libraryConfig
        this.context = libraryConfig.application
    }
}