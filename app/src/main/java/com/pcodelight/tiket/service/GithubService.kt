package com.pcodelight.tiket.service

import com.pcodelight.tiket.Api
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String = "",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = Api.OBJECT_PER_PAGE
    ) : Call<UserResponse>
}