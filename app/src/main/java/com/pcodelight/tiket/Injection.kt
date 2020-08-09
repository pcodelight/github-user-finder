package com.pcodelight.tiket

import androidx.lifecycle.ViewModelProvider
import com.pcodelight.tiket.model.UserDataRepository
import com.pcodelight.tiket.model.UserDataSource
import com.pcodelight.tiket.viewmodel.ViewModelFactory

object Injection {
    private val userDataSource: UserDataSource = UserDataRepository()
    private val userViewModelFactory = ViewModelFactory(userDataSource)

    fun provideUserRepository(): UserDataSource = userDataSource
    fun provideViewModelFactory(): ViewModelProvider.Factory = userViewModelFactory
}