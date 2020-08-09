package com.pcodelight.tiket.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.pcodelight.tiket.Injection
import com.pcodelight.tiket.R
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.ui.LoadingItem
import com.pcodelight.tiket.ui.NoActivityItem
import com.pcodelight.tiket.ui.SearchBoxItem
import com.pcodelight.tiket.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var endlessScrollListener: EndlessRecyclerOnScrollListener

    private val itemAdapter = ItemAdapter<AbstractItem<*>>()
    private val footerAdapter = ItemAdapter<AbstractItem<*>>()
    private val adapter: GenericFastAdapter = FastAdapter.with(
        listOf(itemAdapter, footerAdapter)
    )
    private val viewModel: UserViewModel = ViewModelProvider(
        this,
        Injection.provideViewModelFactory()
    ).get(
        UserViewModel::class.java
    )

    private var query = ""
    private val usersObserver = Observer<List<User>> {

    }

    private val errorMessageObserver = Observer<String> {

    }

    private val loadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            itemAdapter.add(LoadingItem { mode = LoadingItem.Mode.FULL })
        }
    }

    private val loadingMoreObserver = Observer<Boolean> { isLoadingMore ->
        footerAdapter.clear()
        if (isLoadingMore) {
            footerAdapter.add(LoadingItem { mode = LoadingItem.Mode.SCALED })
        }
    }

    private val queryObserver = Observer<String> { query ->
        this.query = query
    }

    private val onSearch: (String) -> Unit = {
        viewModel.searchUsers(it, 1)
    }

    private fun loadMore(currentPage: Int) {
        viewModel.searchUsers(query, currentPage + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvParent.adapter = adapter
        endlessScrollListener = object: EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                loadMore(currentPage)
            }
        }

        initRender()
        initViewModel()
    }

    private fun initRender() {
        itemAdapter.add(
            SearchBoxItem {
                onSearchListener = onSearch
                text = ""
            },
            NoActivityItem()
        )
    }

    private fun render(users: List<User>) {
        itemAdapter.add(
            SearchBoxItem {
                onSearchListener = onSearch
                text = ""
            }
        )
    }

    private fun initViewModel() {
        viewModel.run {
            users.observe(this@MainActivity, usersObserver)
            errorMsg.observe(this@MainActivity, errorMessageObserver)
            isLoading.observe(this@MainActivity, loadingObserver)
            isLoadingMore.observe(this@MainActivity, loadingMoreObserver)
            query.observe(this@MainActivity, queryObserver)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
