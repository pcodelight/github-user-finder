package com.pcodelight.tiket.ui

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R
import kotlinx.android.synthetic.main.footer_loading_item_layout.view.*

class LoadingItem(init: State.() -> Unit) : AbstractItem<LoadingItem.ViewHolder>() {
    private val state = State().apply(init)
    override val layoutRes: Int
        get() = R.layout.footer_loading_item_layout
    override val type: Int
        get() = LoadingItem::class.java.hashCode()

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    class ViewHolder(private val view: View): FastAdapter.ViewHolder<LoadingItem>(view) {
        override fun bindView(item: LoadingItem, payloads: List<Any>) {
            when (item.state.mode) {
                Mode.FULL -> view.flContainer.layoutParams = RecyclerView.LayoutParams(
                    MATCH_PARENT,
                    MATCH_PARENT
                )
                Mode.SCALED -> view.flContainer.layoutParams = RecyclerView.LayoutParams(
                    MATCH_PARENT,
                    WRAP_CONTENT
                )
            }
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