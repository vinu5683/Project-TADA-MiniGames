package com.example.minigamestada.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnlineUsersViewModel @Inject public constructor(val repository: GameRepository) :
    ViewModel() {

    var onlineUsersList = MutableLiveData<ArrayList<OnlineUser>>()

    fun addMeToOnline(onlineUser: OnlineUser) {
        repository.addMeToOnline(onlineUser)
    }

    fun setMeOffline(id: String) {
        repository.setMeOffline(id)
    }

    fun getAllOnlineUsers(userId: String): MutableLiveData<ArrayList<OnlineUser>> {

        viewModelScope.launch {
            repository.getAllOnlineUsers(userId)
            onlineUsersList = repository.onlineUsersList
        }

        return onlineUsersList
    }
}