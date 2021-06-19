package com.example.minigamestada

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.minigamestada.localdatabases.LocalKeys
import com.example.minigamestada.localdatabases.PreferenceHelper
import com.example.minigamestada.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    val SPLASH_LENGTH: Long = 5000

    private lateinit var imageView: ImageView
    private lateinit var tvBelowLogo: TextView
    private lateinit var navController: NavController

    private lateinit var googleSignInAccount: GoogleSignInAccount

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.imgLogo)
        tvBelowLogo = view.findViewById(R.id.tvBelowLogo)
        navController = Navigation.findNavController(view)
        PreferenceHelper.getSharedPreferences(view.context)
        setAnimation(view)

    }

    override fun onStart() {
        super.onStart()
        if (PreferenceHelper.readBooleanFromPreference(LocalKeys.KEY_IS_USER_LOGGEDIN)) {
            if (GoogleSignIn.getLastSignedInAccount(view?.context) != null) {
                //Check for signed in with google
                googleSignInAccount = GoogleSignIn.getLastSignedInAccount(view?.context)!!
                Handler().postDelayed(Runnable {
                    viewModel.getUserDetails(PreferenceHelper.readStringFromPreference(LocalKeys.KEY_USER_GOOGLE_ID))
                    navController!!.navigate(R.id.action_splashFragment_to_homeFragment)
                }, SPLASH_LENGTH)
            } else {
                //check for signed in with gmail??
                Handler().postDelayed(Runnable {
                    viewModel.getUserDetails(PreferenceHelper.readStringFromPreference(LocalKeys.KEY_USER_GOOGLE_ID))
                    navController!!.navigate(R.id.action_splashFragment_to_getStartedFragment)
                }, SPLASH_LENGTH)
            }
        } else {
            Handler().postDelayed(Runnable {
                navController!!.navigate(R.id.action_splashFragment_to_getStartedFragment)
            }, SPLASH_LENGTH)
        }
    }

    private fun setAnimation(view: View) {
        val top = AnimationUtils.loadAnimation(view.context, R.anim.slide_top_bottom)
        imageView.animation = top
        val left = AnimationUtils.loadAnimation(view.context, R.anim.slide_left_right)
        tvBelowLogo.animation = left
    }


}