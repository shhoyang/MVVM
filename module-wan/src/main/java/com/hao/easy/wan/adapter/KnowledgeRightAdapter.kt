package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.databinding.WanItemKnowledgeRightBinding
import com.hao.easy.wan.model.Knowledge
import com.mvvm.adapter.BaseNormalAdapter
import com.mvvm.adapter.ViewHolder

/**
 * @author Yang Shihao
 */
class KnowledgeRightAdapter : BaseNormalAdapter<WanItemKnowledgeRightBinding, Knowledge>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemKnowledgeRightBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemKnowledgeRightBinding>,
        item: Knowledge,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            root.text = item.name
            root.setOnClickListener {
                itemClickListener?.itemClicked(it, item, position)
            }
        }
    }
}