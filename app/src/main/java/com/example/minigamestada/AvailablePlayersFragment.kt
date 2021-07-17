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
import com.example.minigamestada.localdatabases.LocalKeys
import com.example.minigamestada.localdatabases.PreferenceHelper
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.recyclerviews.onlineplayers.OnlinePlayersAdapter
import com.example.minigamestada.viewmodels.GameHistoryViewModel
import com.example.minigamestada.viewmodels.OnlineUsersViewModel
import com.example.minigamestada.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AvailablePlayersFragment : Fragment() {

    private lateinit var v: View
    val userViewModel by viewModels<UserViewModel>()
    val onlineUserViewModel by viewModels<OnlineUsersViewModel>()
    val gameHistoryViewModel by viewModels<GameHistoryViewModel>()
    var onlinePlayersList: ArrayList<OnlineUser> = ArrayList<OnlineUser>()
    var onlinePlayersAdapter: OnlinePlayersAdapter = OnlinePlayersAdapter(onlinePlayersList)


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
        PreferenceHelper.getSharedPreferences(view.context)
        v = view
        initViewsAndListeners()
        setRecyclerViewStuff()
    }

    private fun initViewsAndListeners() {
        recyclerView = v.findViewById(R.id.rvOnlinePlayers)
        txtNumberOfOnlinePlayers = v.findViewById(R.id.txtNumberOfOnlinePlayers)
        recyclerView.layoutManager = GridLayoutManager(v.context, 2)
    }

    private fun setRecyclerViewStuff() {
        onlinePlayersList.add(OnlineUser("asd", "asd", "asd", "N/A", "asdsaf"))
        onlineUserViewModel.getAllOnlineUsers(
            PreferenceHelper.readStringFromPreference(
                LocalKeys.KEY_USER_GOOGLE_ID
            )
        ).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                onlinePlayersList = it
                onlinePlayersAdapter = OnlinePlayersAdapter(onlinePlayersList)
                recyclerView.adapter = onlinePlayersAdapter
                txtNumberOfOnlinePlayers.text = it.size.toString()
                onlinePlayersAdapter.notifyDataSetChanged()
                Log.d("TAG", "setRecyclerViewStuff: ")
            }
            Log.d("TAG", "setRecyclerViewStuff: " + it.size)
        })

    }


}