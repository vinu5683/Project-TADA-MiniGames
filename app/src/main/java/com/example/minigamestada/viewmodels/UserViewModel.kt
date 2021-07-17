package com.example.minigamestada.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minigamestada.localdatabases.LocalKeys
import com.example.minigamestada.localdatabases.PreferenceHelper
import com.example.minigamestada.models.UserModel
import com.example.minigamestada.repository.GameRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject public constructor(val repository: GameRepository) : ViewModel() {

    fun getUserDetails(userId: String?): UserModel? {
        return repository.getCurrentUserDetails(userId)
    }

    fun getUser(): MutableLiveData<UserModel>? {
        return repository.getUser()
    }

    fun saveUser(account: GoogleSignInAccount) {
        repository.saveUser(account)
    }

    fun addToFriendsList(myId: String, friendId: String) {
        repository.addToFriendsList(myId, friendId)
    }

    fun getAllMyFriends(myId: String) {
        repository.getAllMyFriends(myId)
    }


}