package com.pcodelight.tiket.service

import com.pcodelight.tiket.model.User

data class UserResponse(
    val items: List<User>,
    val message: String
)