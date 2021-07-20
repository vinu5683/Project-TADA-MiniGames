package com.example.minigamestada.recyclerviews.onlineplayers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minigamestada.R
import com.example.minigamestada.models.OnlineUser


class OnlinePlayersAdapter(
    val listOfOnlinePlayers: ArrayList<OnlineUser>,
    val onlinePlayersEventListener: OnlinePlayersEventListener
) :
    RecyclerView.Adapter<OnlinePlayersViewHolder>() {
    val hashSet = HashSet<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlinePlayersViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.players_available_list_layout, parent, false)
        return OnlinePlayersViewHolder(v)
    }

    override fun onBindViewHolder(holder: OnlinePlayersViewHolder, position: Int) {
        val onlineuser = listOfOnlinePlayers[position]
        if (!hashSet.contains(onlineuser.id!!)) {
            holder.setData(onlineuser,onlinePlayersEventListener)
            hashSet.add(onlineuser.id)
        }
    }

    override fun getItemCount(): Int {
        return listOfOnlinePlayers.size
    }
}