package com.pcodelight.tiket.model

import com.pcodelight.tiket.service.ApiCallback
import com.pcodelight.tiket.service.UserResponse

interface UserDataSource {
    fun searchUser(query: String, page: Int, callback: ApiCallback<User>)
    fun cancel()
}