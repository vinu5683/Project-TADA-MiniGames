package com.example.minigamestada.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minigamestada.models.GameHistory
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OnlineUsersViewModel @Inject public constructor(val repository: GameRepository) :
    ViewModel() {

    fun addMeToOnline(onlineUser:OnlineUser) {
        repository.addMeToOnline(onlineUser)
    }

    fun setMeOffline(id: String) {
        repository.setMeOffline(id)
    }

    fun getAllOnlineUsers(): MutableLiveData<ArrayList<OnlineUser>> {
        return repository.getAllOnlineUsers()
    }
}