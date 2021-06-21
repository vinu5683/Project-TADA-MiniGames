package com.example.minigamestada.viewmodels

import androidx.lifecycle.ViewModel
import com.example.minigamestada.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SinglePlayerViewModel @Inject public constructor(val repository: GameRepository) :
    ViewModel() {

}