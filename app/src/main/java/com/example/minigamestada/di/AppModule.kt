package com.example.minigamestada.di

import com.example.minigamestada.localdatabases.LocalKeys.BASE_URL
import com.example.minigamestada.models.PushApiService
import com.example.minigamestada.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRepository(): GameRepository {
        return GameRepository()
    }

//    @Singleton
//    @Provides
//    fun providesSearchApi(): PushApiService {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL).build().create(PushApiService::class.java)
//    }
}