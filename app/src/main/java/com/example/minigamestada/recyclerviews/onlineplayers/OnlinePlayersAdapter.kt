package com.example.minigamestada.recyclerviews.onlineplayers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minigamestada.R
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.recyclerviews.onlineplayers.OnlinePlayersViewHolder

class OnlinePlayersAdapter(val listOfOnlinePlayers: ArrayList<OnlineUser>) :
    RecyclerView.Adapter<OnlinePlayersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlinePlayersViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.players_available_list_layout, parent, false)
        return OnlinePlayersViewHolder(v)
    }

    override fun onBindViewHolder(holder: OnlinePlayersViewHolder, position: Int) {
        val onlineuser = listOfOnlinePlayers[position]
        holder.setData(onlineuser)
    }

    override fun getItemCount(): Int {
        return listOfOnlinePlayers.size
    }
}