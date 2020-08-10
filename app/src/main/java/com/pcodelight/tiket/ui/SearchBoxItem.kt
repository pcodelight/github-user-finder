package com.pcodelight.tiket.ui

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.R
import kotlinx.android.synthetic.main.search_box_item_layout.view.*

class SearchBoxItem(private val init: State.() -> Unit) : AbstractItem<SearchBoxItem.ViewHolder>() {
    override val layoutRes: Int
        get() = R.layout.search_box_item_layout
    override val type: Int
        get() = SearchBoxItem::class.java.hashCode()

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v, init)

    class ViewHolder(private val view: View, val init: State.() -> Unit) :
        FastAdapter.ViewHolder<SearchBoxItem>(view) {
        override fun bindView(item: SearchBoxItem, payloads: List<Any>) {
            val state = State().apply {
                init()
            }

            view.etSearch.apply {
                setText(state.text)
                setOnEditorActionListener { _, _, _ ->
                    text.toString().takeIf { it.isNotBlank() }?.let {
                        state.onSearchListener?.invoke(it)
                    }
                    false
                }
            }

            view.btnSearch.setOnClickListener {
                val text = view.etSearch.text.toString()
                state.text = text
                state.onSearchListener?.invoke(text)
            }
        }

        override fun unbindView(item: SearchBoxItem) {}
    }

    class State(
        var onSearchListener: ((String) -> Unit)? = null,
        var text: String? = null
    )
}