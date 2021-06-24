package com.mvvm.view.dialog

import android.app.Activity
import android.view.Gravity
import android.view.Window
import androidx.annotation.StyleRes
import com.mvvm.MVVMLibrary
import com.mvvm.R
import com.mvvm.databinding.DialogLoadingBinding
import com.mvvm.utils.DisplayUtils

/**
 * @author Yang Shihao
 */
class LoadingDialog(
    activity: Activity,
    @StyleRes themeResId: Int = MVVMLibrary.CONFIG.loadingDialogTheme
) :
    BaseDialog<DialogLoadingBinding>(activity, themeResId) {

    constructor(activity: Activity) : this(activity, MVVMLibrary.CONFIG.loadingDialogTheme)

    private var widthRate = 1.0F
    private var assetAssetName = "def_loading.json"

    override fun getVB() = DialogLoadingBinding.inflate(layoutInflater)

    override fun parseTheme() {
        context.obtainStyledAttributes(R.styleable.LoadingDialog).apply {
            widthRate = getFloat(R.styleable.LoadingDialog_loadingDialogWidthRate, widthRate)
            assetAssetName =
                getString(R.styleable.LoadingDialog_loadingDialogAssetFileName) ?: assetAssetName
            recycle()
        }
    }

    override fun setWindowParams(window: Window) {
        val attributes = window.attributes
        val width = (DisplayUtils.getScreenWidth(activity) * widthRate).toInt()
        attributes.width = width
        attributes.height = width
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes

    }

    override fun initView() {
        viewBinding {
            root.setAnimation(assetAssetName)
        }
    }

    fun setMessage(message: String? = null): LoadingDialog {
        return this
    }
}