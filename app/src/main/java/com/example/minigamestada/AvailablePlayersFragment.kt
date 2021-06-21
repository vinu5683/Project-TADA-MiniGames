package com.example.minigamestada

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.recyclerviews.onlineplayers.OnlinePlayersAdapter
import com.example.minigamestada.viewmodels.GameHistoryViewModel
import com.example.minigamestada.viewmodels.OnlineUsersViewModel
import com.example.minigamestada.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvailablePlayersFragment : Fragment() {

    private lateinit var v: View
    val userViewModel by viewModels<UserViewModel>()
    val onlineUserViewModel by viewModels<OnlineUsersViewModel>()
    val gameHistoryViewModel by viewModels<GameHistoryViewModel>()

    lateinit var onlinePlayersAdapter: OnlinePlayersAdapter

    var onlinePlayersList: ArrayList<OnlineUser>? = null

    lateinit var recyclerView: RecyclerView
    lateinit var txtNumberOfOnlinePlayers: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        initViewsAndListeners()
        getOnlinePlayersList()
        setRecyclerViewStuff()
    }

    private fun setRecyclerViewStuff() {
        if (onlinePlayersList != null) {
            onlinePlayersAdapter = OnlinePlayersAdapter(onlinePlayersList!!)
            recyclerView.adapter = onlinePlayersAdapter
        }
    }

    private fun initViewsAndListeners() {
        recyclerView = v.findViewById(R.id.rvOnlinePlayers)
        txtNumberOfOnlinePlayers = v.findViewById(R.id.txtNumberOfOnlinePlayers)
        recyclerView.layoutManager = GridLayoutManager(v.context, 2)
    }

    override fun onStart() {
        super.onStart()

        userViewModel.getUser()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                onlineUserViewModel.addMeToOnline(
                    OnlineUser(
                        it.id,
                        it.name,
                        "online",
                        it.profile_pic,
                        it.token
                    )
                )
            } else {
                Log.d("TAG", "onStart: Sorry Not Able To Make you Online")
            }
            getOnlinePlayersList()
        })

    }

    override fun onDestroy() {
        super.onDestroy()

        val userId = userViewModel.getUser()?.value?.id
        if (userId != null) {
            onlineUserViewModel.setMeOffline(userId)
        } else {
            Log.d("TAG", "onStart: Sorry Not Able To Remove From Online")
        }
    }

    private fun getOnlinePlayersList() {
        onlineUserViewModel.getAllOnlineUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                onlinePlayersList = it
            }
        })

    }


}