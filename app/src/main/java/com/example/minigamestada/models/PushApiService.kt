package com.example.minigamestada.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PushApiService {

    @POST("notification/data")
    fun sendNotification(
        @Header("token") token: String,
        @Header("title") title: String,
        @Header("message") message: String
    ): Call<Boolean>

}