package com.example.minigamestada

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.minigamestada.localdatabases.LocalKeys
import com.example.minigamestada.localdatabases.PreferenceHelper
import com.example.minigamestada.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.shobhitpuri.custombuttons.GoogleSignInButton
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var gso: GoogleSignInOptions
    lateinit var navController: NavController
    lateinit var googleSignInClient: GoogleSignInClient
    val SIGN_IN_CODE = 10

    val userViewModel by viewModels<UserViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        PreferenceHelper.getSharedPreferences(view.context)

        initializeGoogleSignin(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_CODE) {
            var task: Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            if (task!!.isSuccessful) {
                val account = task.getResult(ApiException::class.java)
                updatePreference(account!!)
                userViewModel.saveUser(account!!)
                Toast.makeText(view?.context, "Welcome ${account.displayName}", Toast.LENGTH_SHORT)
                    .show()
                navController!!.navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    view?.context,
                    "Login Error " + task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: Exception) {

        }
    }

    private fun updatePreference(account: GoogleSignInAccount) {
        PreferenceHelper.writeBooleanToPreference(LocalKeys.KEY_IS_USER_LOGGEDIN, true)
        PreferenceHelper.writeStringToPreference(LocalKeys.KEY_USER_GOOGLE_ID, account!!.id)
    }

    private fun initializeGoogleSignin(view: View) {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        val signInButton = view.findViewById<GoogleSignInButton>(R.id.signInButton);
        signInButton.setOnClickListener {
            val intent: Intent = googleSignInClient.signInIntent
            startActivityForResult(intent, SIGN_IN_CODE)
        }
    }

}