package com.mvvm.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
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

fun ImageView.load(url: Any, @DrawableRes placeholder: Int) {
    val options = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
    Glide.with(this).load(url).apply(options).into(this)
}

fun ImageView.loadCircle(url: Any, @DrawableRes placeholder: Int = 0) {
    val requestOptions = RequestOptions.bitmapTransform(CircleCrop())
    if (placeholder != 0) {
        requestOptions.placeholder(placeholder).error(placeholder)
    }
    Glide.with(this).load(url).apply(requestOptions).into(this)
}


fun ImageView.loadRounded(url: Any, radius: Int, @DrawableRes placeholder: Int = 0) {
    val requestOptions = RequestOptions.bitmapTransform(RoundedCorners(radius))
    if (placeholder != 0) {
        requestOptions.placeholder(placeholder).error(placeholder)
    }
    Glide.with(this).load(url).apply(requestOptions).into(this)
}