package com.pcodelight.tiket.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    fun searchUser(@Query("q") username: String = "") : Call<UserResponse>
}