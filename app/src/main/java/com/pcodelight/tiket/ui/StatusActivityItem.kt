package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R

class StatusActivityItem : AbstractItem<StatusActivityItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.no_activity_item_layout
    override val type: Int
        get() = StatusActivityItem::class.java.hashCode()
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(val view: View): FastAdapter.ViewHolder<StatusActivityItem>(view) {
        override fun bindView(item: StatusActivityItem, payloads: List<Any>) {}
        override fun unbindView(item: StatusActivityItem) {}
    }
}