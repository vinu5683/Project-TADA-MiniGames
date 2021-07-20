package com.example.minigamestada.fcm

import com.example.minigamestada.localdatabases.LocalKeys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Network {
    companion object {
        val logginInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        fun getInstance(): Retrofit {
            val builder = Retrofit.Builder()
                .baseUrl(LocalKeys.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(logginInterceptor).build())
            return builder.build()
        }

    }


}