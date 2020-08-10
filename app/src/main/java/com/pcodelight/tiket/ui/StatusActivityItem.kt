package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R
import kotlinx.android.synthetic.main.no_activity_item_layout.view.*

class StatusActivityItem(init: State.() -> Unit) : AbstractItem<StatusActivityItem.ViewHolder>() {
    val state = State().apply(init)
    override val layoutRes: Int
        get() = R.layout.no_activity_item_layout
    override val type: Int
        get() = StatusActivityItem::class.java.hashCode()
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(private val view: View): FastAdapter.ViewHolder<StatusActivityItem>(view) {
        override fun bindView(item: StatusActivityItem, payloads: List<Any>) {
           view.tvMessage.text = item.state.message
        }
        override fun unbindView(item: StatusActivityItem) {}
    }

    class State {
        var message: String = ""
    }
}