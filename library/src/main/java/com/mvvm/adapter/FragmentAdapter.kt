package com.mvvm.adapter

import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mvvm.ui.FragmentCreator

/**
 * @author Yang Shihao
 */
class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var data: List<FragmentCreator>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments = SparseArrayCompat<Fragment>()

    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        var f = fragments[position]
        if (f == null) {
            f = data[position].createFragment()
            fragments.put(position, f)
        }
        return f
    }

    fun getFragment(position: Int): Fragment? {
        return fragments.get(position)
    }
}



