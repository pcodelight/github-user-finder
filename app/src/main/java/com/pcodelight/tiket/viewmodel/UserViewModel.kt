package com.pcodelight.tiket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.model.UserDataSource
import com.pcodelight.tiket.service.ApiCallback

class UserViewModel(private val repo: UserDataSource): ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply {
        value = emptyList()
    }
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isEmpty = MutableLiveData<Boolean>()
    private val _errorMsg = MutableLiveData<String>()

    val users: LiveData<List<User>> = _users
    val isLoading: LiveData<Boolean> = _isLoading
    val isEmpty: LiveData<Boolean> = _isEmpty
    val errorMsg: LiveData<String> = _errorMsg

    fun searchUsers(query: String, page: Int) {
        _isLoading.postValue(true)

        repo.searchUser(query, page, object: ApiCallback<User> {
            override fun onSuccess(data: List<User>?) {
                _isLoading.postValue(false)
                data?.let {
                    if (it.isNotEmpty()) {
                        _users.postValue(it)
                    } else {
                        _isEmpty.postValue(true)
                    }
                }
            }

            override fun onError(error: String?) {
                _isLoading.postValue(false)
                _errorMsg.postValue(error)
            }
        })
    }
}