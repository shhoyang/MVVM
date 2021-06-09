package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.databinding.WanItemBannerBinding
import com.hao.easy.wan.model.Ad
import com.mvvm.adapter.BaseNormalAdapter
import com.mvvm.adapter.ViewHolder
import com.mvvm.extensions.load

/**
 * @author Yang Shihao
 */
class BannerAdapter : BaseNormalAdapter<WanItemBannerBinding, Ad>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemBannerBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemBannerBinding>,
        item: Ad,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding { root.load(item.imagePath) }
    }
}