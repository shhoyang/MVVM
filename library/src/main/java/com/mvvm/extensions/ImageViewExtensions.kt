package com.mvvm.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * @author Yang Shihao
 */

fun ImageView.load(url: Any) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.load(url: Any, placeholder: Int) {
    val options = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
    Glide.with(this).load(url).apply(options).into(this)
}

fun ImageView.loadCircle(url: Any) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}

fun ImageView.loadRounded(url: Any, radius: Int) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
        .into(this)
}