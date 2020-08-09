package com.pcodelight.tiket.service

interface ApiCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}