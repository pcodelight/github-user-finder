package com.pcodelight.tiket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pcodelight.tiket.model.UserDataSource

class ViewModelFactory(private val repo: UserDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repo) as T
    }
}