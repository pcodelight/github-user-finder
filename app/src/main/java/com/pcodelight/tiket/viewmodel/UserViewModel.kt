package com.pcodelight.tiket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.model.UserDataSource
import com.pcodelight.tiket.service.ApiCallback

class UserViewModel(private val repo: UserDataSource) : ViewModel() {
    private val _users = MutableLiveData<List<User>>().apply {
        value = emptyList()
    }
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMsg = MutableLiveData<String>()
    private val _query = MutableLiveData<String>()
    private val _isLoadingMore = MutableLiveData<Boolean>()

    val users: LiveData<List<User>> = _users
    val isLoading: LiveData<Boolean> = _isLoading
    val isLoadingMore: LiveData<Boolean> = _isLoadingMore
    val errorMsg: LiveData<String> = _errorMsg
    val query: LiveData<String> = _query

    fun searchUsers(query: String, page: Int) {
        _query.postValue(query)

        _isLoading.postValue(page == 1)
        _isLoadingMore.postValue(page > 1)

        repo.searchUser(query, page, object : ApiCallback<User> {
            override fun onSuccess(data: List<User>?) {
                _isLoading.postValue(false)
                _isLoadingMore.postValue(false)

                data?.let {
                    if (page > 1) {
                        _users.postValue(_users.value?.plus(it))
                    } else {
                        _users.postValue(it)
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