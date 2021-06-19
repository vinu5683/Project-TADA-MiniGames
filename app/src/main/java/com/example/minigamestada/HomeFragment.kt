package com.example.minigamestada

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.minigamestada.localdatabases.LocalKeys
import com.example.minigamestada.localdatabases.PreferenceHelper
import com.example.minigamestada.models.UserModel
import com.example.minigamestada.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    var btnTTT: Button? = null
    var btnMATH: Button? = null
    var btnSUDOKU: Button? = null
    lateinit var navController: NavController

    val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PreferenceHelper.getSharedPreferences(view.context)
        navController = Navigation.findNavController(view)

        userViewModel.getUser()?.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Log.d("TAG", "onViewCreated:home: " + it.toString())
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            } else {
                Log.d("TAG", "onViewCreated: $it")

            }
        })

        view.apply {
            btnMATH = findViewById(R.id.mathguess_game_btn)
            btnTTT = findViewById(R.id.tictactoe_game_btn)
            btnSUDOKU = findViewById(R.id.sudoku_game_btn)
        }
        listeners(view)

    }

    private fun listeners(view: View) {
        btnTTT?.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_ticTacToeFragment)
        }
        btnMATH?.setOnClickListener {

        }
        btnSUDOKU?.setOnClickListener {

        }
    }


}