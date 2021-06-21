package com.example.minigamestada.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minigamestada.models.GameHistory
import com.example.minigamestada.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameHistoryViewModel @Inject public constructor(val repository: GameRepository) :
    ViewModel() {

    fun getGameHistoryByUserId(id: String): MutableLiveData<ArrayList<GameHistory>> {
        return repository.getGameHistoryByUserId(id)
    }

    fun addToGameHistory(gameHistory: GameHistory) {
        repository.addToGameHistory(gameHistory)
    }
}
