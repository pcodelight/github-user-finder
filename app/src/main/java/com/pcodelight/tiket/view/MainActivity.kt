package com.pcodelight.tiket.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.pcodelight.tiket.Injection
import com.pcodelight.tiket.R
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.ui.LoadingItem
import com.pcodelight.tiket.ui.StatusActivityItem
import com.pcodelight.tiket.ui.SearchBoxItem
import com.pcodelight.tiket.ui.UserItem
import com.pcodelight.tiket.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener

    private val itemAdapter = ItemAdapter<AbstractItem<*>>()
    private val footerAdapter = ItemAdapter<AbstractItem<*>>()
    private val headerAdapter = ItemAdapter<AbstractItem<*>>()
    private val rvAdapter: GenericFastAdapter = FastAdapter.with(
        listOf(
            headerAdapter,
            itemAdapter,
            footerAdapter
        )
    )
    private lateinit var viewModel: UserViewModel
    private var query = ""
    private var firstLoad = true

    private val usersObserver = Observer<List<User>> {
        Log.d("UserFinder", "Data received ${it.size}")
        itemAdapter.set(
            it.map{ user ->
                UserItem {
                    photoUrl = user.getSmallPhotoUrl()
                    name = user.name
                }
            }
        )
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
        Log.d("UserFinder", "Query Search on $it")
        viewModel.searchUsers(it, 1)
    }

    private fun loadMore(currentPage: Int) {
        Log.d("UserFinder", "LoadMore With Current Page $currentPage")
        viewModel.searchUsers(query, currentPage + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRender()
        initViewModel()
    }

    private fun initRender() {
        rvParent.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, VERTICAL, false)
            endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
                override fun onLoadMore(currentPage: Int) {
                    if (!firstLoad) {
                        loadMore(currentPage)
                        firstLoad = false
                    }
                }
            }
            addOnScrollListener(endlessRecyclerOnScrollListener)
        }

        headerAdapter.add(
            SearchBoxItem {
                onSearchListener = onSearch
                text = ""
            }
        )

        itemAdapter.add(
            StatusActivityItem()
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(
            UserViewModel::class.java
        ).apply {
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
