package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R

class UserItem : AbstractItem<UserItem.ViewHolder>() {
    override val layoutRes: Int get() = R.layout.user_item_layout
    override val type: Int get() = UserItem::class.java.hashCode()
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(val view: View): FastAdapter.ViewHolder<UserItem>(view) {
        override fun bindView(item: UserItem, payloads: List<Any>) {

        }

        override fun unbindView(item: UserItem) {
            // remove glide load
        }
    }

    data class State(
        val photoUrl: String,
        val name: String
    )
}