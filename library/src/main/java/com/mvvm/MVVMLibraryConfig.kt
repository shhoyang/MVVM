package com.mvvm

import android.app.Application
import androidx.annotation.StyleRes

/**
 * @author Yang Shihao
 */
class MVVMLibraryConfig private constructor(val application: Application) {

    var toolbarLayoutTheme = R.style.ToolbarLayout
    var emptyViewTheme = R.style.EmptyView
    var confirmDialogTheme = R.style.ConfirmDialog
    var loadingDialogTheme = R.style.LoadingDialog

    lateinit var httpHandler: HttpHandler
    lateinit var loginHandler: LoginHandler

    class Builder(application: Application) {

        private var config: MVVMLibraryConfig = MVVMLibraryConfig(application)

        fun setToolbarLayoutTheme(@StyleRes themeResId: Int): Builder {
            config.toolbarLayoutTheme = themeResId
            return this
        }

        fun setEmptyViewTheme(@StyleRes themeResId: Int): Builder {
            config.emptyViewTheme = themeResId
            return this
        }

        fun setConfirmDialogTheme(@StyleRes themeResId: Int): Builder {
            config.confirmDialogTheme = themeResId
            return this
        }

        fun setLoadingDialogTheme(@StyleRes themeResId: Int): Builder {
            config.loadingDialogTheme = themeResId
            return this
        }

        fun setHttpHandler(httpHandler: HttpHandler): Builder {
            config.httpHandler = httpHandler
            return this
        }

        fun setLoginHandler(loginHandler: LoginHandler): Builder {
            config.loginHandler = loginHandler
            return this
        }

        fun apply() {
            MVVMLibrary.init(config)
        }
    }
}