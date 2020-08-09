package com.pcodelight.tiket.ui

import android.view.View
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UserItem(init: State.() -> Unit) : AbstractItem<UserItem.ViewHolder>() {
    private val state = State().apply(init)
    override val layoutRes: Int get() = R.layout.user_item_layout
    override val type: Int get() = UserItem::class.java.hashCode()
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(private val view: View) :
        FastAdapter.ViewHolder<UserItem>(view) {

        override fun bindView(item: UserItem, payloads: List<Any>) {
            Glide.with(view)
                .load(item.state.photoUrl)
                .fitCenter()
                .circleCrop()
                .into(view.ivPhoto)
            view.tvName.text = item.state.name
        }

        override fun unbindView(item: UserItem) {}
    }

    data class State(
        var photoUrl: String = "",
        var name: String = ""
    )
}