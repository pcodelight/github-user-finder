package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R

class FooterLoadingItem : AbstractItem<FooterLoadingItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.footer_loading_item_layout
    override val type: Int
        get() = FooterLoadingItem::class.java.hashCode()

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    class ViewHolder(view: View): FastAdapter.ViewHolder<FooterLoadingItem>(view) {
        override fun bindView(item: FooterLoadingItem, payloads: List<Any>) {

        }

        override fun unbindView(item: FooterLoadingItem) {}
    }
}