package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R

class SearchBoxItem: AbstractItem<SearchBoxItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.search_box_item_layout
    override val type: Int
        get() = SearchBoxItem::class.java.hashCode()
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(val view: View): FastAdapter.ViewHolder<SearchBoxItem>(view) {
        override fun bindView(item: SearchBoxItem, payloads: List<Any>) {
        }

        override fun unbindView(item: SearchBoxItem) {
        }
    }
}