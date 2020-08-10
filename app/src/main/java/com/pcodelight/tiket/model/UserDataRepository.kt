package com.pcodelight.tiket.model

import com.pcodelight.tiket.Api
import com.pcodelight.tiket.service.ApiCallback
import com.pcodelight.tiket.service.GithubService
import com.pcodelight.tiket.service.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataRepository: UserDataSource {
    private var call: Call<UserResponse>? = null

    override fun searchUser(query: String, page: Int, callback: ApiCallback<User>) {
        call = Api.instance.create(GithubService::class.java)
            .searchUser(query, page)

        call?.enqueue(object: Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                callback.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it.items)
                    } else {
                        callback.onError("There is someting wrong :(")
                    }
                } ?: callback.onError(response.message())
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}