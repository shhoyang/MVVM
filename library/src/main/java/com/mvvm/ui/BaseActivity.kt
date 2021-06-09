package com.mvvm.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mvvm.AppManager
import com.mvvm.Dagger
import com.mvvm.R
import com.mvvm.annotation.Base
import com.mvvm.annotation.InjectViewBinding
import com.mvvm.annotation.InjectViewModel
import com.mvvm.utils.DisplayUtils
import com.mvvm.utils.L
import com.mvvm.utils.T
import com.mvvm.view.ToolbarLayout
import com.mvvm.view.dialog.LoadingDialog
import com.mvvm.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    @JvmField
    @InjectViewBinding
    var vb: VB? = null

    @JvmField
    @InjectViewModel
    var vm: VM? = null

    private var toolbarLayout: ToolbarLayout? = null

    private var loadingDialog: LoadingDialog? = null

    protected val uiParams = UIParams()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        L.d("ActivityName", javaClass.simpleName)
        onInitView()
        initView()
        onInitData()
        initData()
    }

    private fun onInitView() {
        prepare(uiParams, intent)
        if (uiParams.isTransparentStatusBar) {
            DisplayUtils.setTransparentStatusBar(this)
        }
        Dagger.inject(this)
        setContentView(vb?.root)
        toolbarLayout = f(R.id.baseToolbar)
        toolbarLayout?.setBackClickListener {
            onBackPressed()
        }
    }

    private fun onInitData(){
        viewModel {
            toastLiveData.observe(this@BaseActivity) {
                this@BaseActivity.toast(it)
            }
            loadingLiveData.observe(this@BaseActivity) {
                if (it) {
                    this@BaseActivity.showLoading()
                } else {
                    this@BaseActivity.hideLoading()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        vb = null
        loadingDialog = null
        AppManager.instance().popActivity(this)
    }

    fun toolbarLayout(block: ToolbarLayout.() -> Unit) {
        toolbarLayout?.let(block)
    }

    override fun setTitle(title: CharSequence) {
        toolbarLayout?.setTitleText(title.toString())
    }

    override fun setTitle(@StringRes titleId: Int) {
        toolbarLayout?.setTitleText(titleId)
    }

    open fun prepare(uiParams: UIParams, intent: Intent?) {
    }

    fun viewBinding(block: VB.() -> Unit) {
        vb?.let(block)
    }

    fun viewModel(block: VM.() -> Unit) {
        vm?.let(block)
    }

    fun showLoading(message: String? = null) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog?.show()
    }

    fun hideLoading() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    fun toast(text: String?) {
        T.short(this, text)
    }

    fun toast(@StringRes resId: Int) {
        T.short(this, resId)
    }

    fun toA(cls: Class<out Activity>, isFinish: Boolean = false) {
        startActivity(Intent(this, cls))
        if (isFinish) {
            finish()
        }
    }

    fun <T : View> f(id: Int): T? {
        return vb?.root?.findViewById(id)
    }

    fun contentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)

    abstract fun initView()

    abstract fun initData()
}