package com.example.minigamestada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.minigamestada.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class TicTacToeFragment : Fragment() {

    val userViewModel by viewModels<UserViewModel>()
    lateinit var profileImageView: CircleImageView
    lateinit var backBtn: ImageButton
    lateinit var singlePlayerBtn: Button
    lateinit var multiPlayerBtn: Button
    lateinit var onlineMultiPlayerBtn: Button
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tic_tac_toe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        view.apply {
            profileImageView = findViewById(R.id.imgViewProfilePic)
            backBtn = findViewById(R.id.imgBtnBackTicTacToe)
            singlePlayerBtn = findViewById(R.id.ttt_singlePlayer_btn)
            multiPlayerBtn = findViewById(R.id.ttt_twoPlayer_btn)
            onlineMultiPlayerBtn = findViewById(R.id.ttt_OnlineMultiPlayer_btn)
        }
        setListeners(view)

        userViewModel.getUser()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.profile_pic != null && it.profile_pic != "" && it.profile_pic != "N/A") {
                    Glide.with(view.context).load(it.profile_pic).into(profileImageView)
                } else {
                    Glide.with(view.context).load(R.drawable.ic_noun_user_temp)
                        .into(profileImageView)
                }
            }
        })
    }

    private fun setListeners(view: View) {
        view.apply {
            backBtn.setOnClickListener {
                activity?.onBackPressed()
            }
            singlePlayerBtn.setOnClickListener {
                navController.navigate(R.id.action_ticTacToeFragment_to_TTT_SingleFragment)
            }
            multiPlayerBtn.setOnClickListener {
                navController.navigate(R.id.action_ticTacToeFragment_to_TTT_Two_OffilneFragment)
            }
            onlineMultiPlayerBtn.setOnClickListener {
                navController.navigate(R.id.action_ticTacToeFragment_to_availablePlayersFragment)
            }

        }
    }

}