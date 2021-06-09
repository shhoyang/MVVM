package com.mvvm.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.mvvm.MVVMLibrary
import com.mvvm.R
import com.mvvm.extensions.visibility
import com.mvvm.utils.DrawableUtils

/**
 * @author Yang Shihao
 */
class ToolbarLayout : FrameLayout {

    private var toolbarBackgroundColor = COLOR_NO
    private var showBack = true
    private var titleText: String? = null
    private var toolbarBack: ImageView? = null
    private var toolbarTitle: TextView? = null

    constructor(context: Context) : this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr, 0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {

        var layoutId: Int

        context.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarLayout,
            defStyleAttr,
            MVVMLibrary.CONFIG.toolbarLayoutTheme
        )
            .apply {
                layoutId = getResourceId(R.styleable.ToolbarLayout_toolbarLayoutId, 0)
                toolbarBackgroundColor =
                    getColor(R.styleable.ToolbarLayout_toolbarBackgroundColor, COLOR_NO)
                showBack = getBoolean(R.styleable.ToolbarLayout_toolbarShowBack, showBack)
                titleText = getString(R.styleable.ToolbarLayout_toolbarTitleText)
                recycle()
            }

        View.inflate(context, layoutId, this)
    }

    fun showBack(show: Boolean) {
        toolbarBack?.visibility(show)
    }

    fun setTitleText(text: String) {
        toolbarTitle?.text = text
    }

    fun setTitleText(@StringRes resId: Int) {
        toolbarTitle?.text = context.getString(resId)
    }

    fun setTitleTextColor(color: Int) {
        toolbarTitle?.setTextColor(color)
    }

    fun setIconColor(color: Int) {
        toolbarBack?.apply { DrawableUtils.tint(this, color) }
    }

    fun setBackClickListener(f: () -> Unit) {
        toolbarBack?.setOnClickListener { f() }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        toolbarBack = findViewById(R.id.toolbarBack)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        if (COLOR_NO != toolbarBackgroundColor) {
            setBackgroundColor(toolbarBackgroundColor)
        }
        toolbarBack?.visibility(showBack)
        toolbarTitle?.text = titleText
    }

    companion object {
        private const val COLOR_NO = -2
    }
}