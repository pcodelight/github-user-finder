package com.pcodelight.tiket.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.pcodelight.tiket.Api
import com.pcodelight.tiket.Injection
import com.pcodelight.tiket.R
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.ui.*
import com.pcodelight.tiket.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener
    private lateinit var viewModel: UserViewModel

    private val itemAdapter = ItemAdapter<AbstractItem<*>>()
    private val footerAdapter = GenericItemAdapter()
    private val headerAdapter = ItemAdapter<AbstractItem<*>>()
    private val rvAdapter: GenericFastAdapter = FastAdapter.with(
        listOf(
            headerAdapter,
            itemAdapter,
            footerAdapter
        )
    )
    private var currentPage: Int = 1
    private var query: String = ""

    private val usersObserver = Observer<List<User>> {
        footerAdapter.clear()

        if (it.isNotEmpty()) {
            itemAdapter.set(
                it.map { user ->
                    UserItem {
                        photoUrl = user.getSmallPhotoUrl()
                        name = user.name
                    }
                }
            )
        } else {
            itemAdapter.set(
                listOf(StatusActivityItem { message = "User Not Found" })
            )
        }
    }

    private val errorMessageObserver = Observer<String> {
        footerAdapter.clear()
        itemAdapter.set(
            listOf(StatusActivityItem { message = it })
        )
    }

    private val loadingObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            itemAdapter.set(listOf(LoadingItem { mode = LoadingItem.Mode.FULL }))
        }
    }

    private val hasNextObserver = Observer<Boolean> {
        enableEndlessScroll(it)
    }

    private val pageObserver = Observer<Int> {
        currentPage = it
    }

    private val queryObserver = Observer<String> {
        query = it
    }

    private val onSearch: (String) -> Unit = {
        if (it.isNotBlank()) {
            footerAdapter.clear()
            itemAdapter.clear()

            endlessRecyclerOnScrollListener.apply {
                disable()
                resetPageCount(0)
            }
            viewModel.searchUsers(it, 1)
        } else {
            Toast.makeText(this@MainActivity, "Fill the name first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableEndlessScroll(enable: Boolean) {
        endlessRecyclerOnScrollListener.apply {
            if (enable) enable() else disable()
        }
    }

    private fun loadMore() {
        val isLoadingFirstPage = viewModel.isLoading.value == true
                && currentPage == 1
                && query.isNotBlank()

        if (!isLoadingFirstPage) {
            footerAdapter.clear()
            footerAdapter.add(LoadingItem { mode = LoadingItem.Mode.SCALED })
            viewModel.searchUsers(
                query,
                currentPage + 1
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initRender()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString("last_query") ?: ""

        val hadSearchingBefore = query.isNotBlank()
        val isUserListRecycled = viewModel.users.value == null
        if (hadSearchingBefore && isUserListRecycled) {
            onSearch(query)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("last_query", query)
    }

    private fun initRender() {
        headerAdapter.add(
            SearchBoxItem {
                query.takeIf { it.isNotBlank() }?.let {
                    text = it
                }
                onSearchListener = onSearch
            }
        )

        rvParent.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, VERTICAL, false)
            endlessRecyclerOnScrollListener =
                object : EndlessRecyclerOnScrollListener(footerAdapter) {
                    override fun onLoadMore(currentPage: Int) {
                        if (itemAdapter.adapterItemCount >= Api.OBJECT_PER_PAGE) {
                            loadMore()
                        }
                    }
                }
            rvParent.addOnScrollListener(endlessRecyclerOnScrollListener)
        }
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
            hasNext.observe(this@MainActivity, hasNextObserver)
            page.observe(this@MainActivity, pageObserver)
            query.observe(this@MainActivity, queryObserver)
        }
    }
}
