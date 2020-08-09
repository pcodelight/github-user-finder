package com.pcodelight.tiket.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.pcodelight.tiket.Injection
import com.pcodelight.tiket.R
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.ui.NoActivityItem
import com.pcodelight.tiket.ui.SearchBoxItem
import com.pcodelight.tiket.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val itemAdapter = ItemAdapter<AbstractItem<*>>()
    private val adapter = FastAdapter.with(itemAdapter)
    private val viewModel: UserViewModel = ViewModelProvider(
        this,
        Injection.provideViewModelFactory()
    ).get(
        UserViewModel::class.java
    )

    private val usersObserver = Observer<List<User>> {
        render()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvParent.adapter = adapter

        initRender()
    }

    private fun initRender() {
        itemAdapter.add(
            SearchBoxItem(),
            NoActivityItem()
        )
    }

    private fun render() {

    }

    private fun initViewModel() {
        viewModel.users.observe(this, )
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchUsers()
    }
}
