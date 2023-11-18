package com.learning.food1.Main

import android.app.Activity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import java.util.Objects
import javax.inject.Inject

open class BaseFragment @Inject constructor(private var activity : Activity?) {

    companion object {
        private const val TAG = "BaseFragment"
    }

    fun getLayoutManager(orientation: Int): LinearLayoutManager {
        return if (orientation == 0) LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        ) else LinearLayoutManager(
            activity
        )
    }

    fun initRv(
        rv: RecyclerView, lm: LinearLayoutManager?, isNestedScroll: Boolean,
        decor: RecyclerView.ItemDecoration?
    ) {
        if (activity != null && !activity!!.isDestroyed && !activity!!.isFinishing) {
            rv.layoutManager = lm
            rv.isNestedScrollingEnabled = isNestedScroll
            rv.itemAnimator = DefaultItemAnimator()
            rv.isFocusable = false
            rv.setItemViewCacheSize(5)
            if (decor != null) {
                rv.addItemDecoration(decor)
            }
            (Objects.requireNonNull(rv.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations =
                false
        }
    }

}