package com.example.minigamestada.recyclerviews.onlineplayers

import com.example.minigamestada.models.OnlineUser

interface OnlinePlayersEventListener {

    fun onAddToFriendsList(onlineUser: OnlineUser)
    fun onSendRequest(onlineUser: OnlineUser)
}