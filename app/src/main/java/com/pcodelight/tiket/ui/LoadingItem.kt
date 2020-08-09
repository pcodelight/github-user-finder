package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R

class LoadingItem(val init: State.() -> Unit) : AbstractItem<LoadingItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.footer_loading_item_layout
    override val type: Int
        get() = LoadingItem::class.java.hashCode()

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    class ViewHolder(view: View): FastAdapter.ViewHolder<LoadingItem>(view) {
        override fun bindView(item: LoadingItem, payloads: List<Any>) {

        }

        override fun unbindView(item: LoadingItem) {}
    }

    class State {
        var mode: Mode = Mode.SCALED
    }

    enum class Mode {
        FULL, SCALED
    }
}