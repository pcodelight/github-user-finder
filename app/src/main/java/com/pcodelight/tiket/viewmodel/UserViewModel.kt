package com.pcodelight.tiket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pcodelight.tiket.Api
import com.pcodelight.tiket.model.User
import com.pcodelight.tiket.model.UserDataSource
import com.pcodelight.tiket.service.ApiCallback

class UserViewModel(private val repo: UserDataSource) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMsg = MutableLiveData<String>()
    private val _query = MutableLiveData<String>()
    private val _page = MutableLiveData<Int>()
    private val _hasNext = MutableLiveData<Boolean>()

    val users: LiveData<List<User>> = _users
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMsg: LiveData<String> = _errorMsg
    val hasNext: LiveData<Boolean> = _hasNext
    val query: LiveData<String> = _query
    val page: LiveData<Int> = _page

    fun searchUsers(query: String, page: Int) {
        _query.postValue(query)
        _page.postValue(page)

        _isLoading.postValue(page == 1)

        repo.cancel()
        repo.searchUser(query, page, object : ApiCallback<User> {
            override fun onSuccess(data: List<User>?) {
                _isLoading.postValue(false)
                _errorMsg.postValue("")

                data?.let {
                    _users.postValue(it)
                    _hasNext.postValue(it.size >= Api.OBJECT_PER_PAGE)
                }
            }

            override fun onError(error: String?) {
                _isLoading.postValue(false)
                _errorMsg.postValue(error)
            }
        })
    }
}