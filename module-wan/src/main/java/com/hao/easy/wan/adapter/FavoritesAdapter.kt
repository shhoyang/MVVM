package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.databinding.WanItemFavoriteBinding
import com.hao.easy.wan.model.Article
import com.mvvm.adapter.BasePagedAdapter
import com.mvvm.adapter.ViewHolder

/**
 * @author Yang Shihao
 */
class FavoritesAdapter : BasePagedAdapter<WanItemFavoriteBinding, Article>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemFavoriteBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemFavoriteBinding>,
        item: Article,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            tvTitle.text = item.title
            ivFavorite.setOnClickListener {
                itemClickListener?.itemClicked(it, item, position)
            }
        }
    }
}