package com.example.minigamestada

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.viewmodels.OnlineUsersViewModel
import com.example.minigamestada.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.InetAddress


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val userViewModel by viewModels<UserViewModel>()
    val onlineUserViewModel by viewModels<OnlineUsersViewModel>()

    companion object {
        var flag = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

//    fun isInternetAvailable(): Boolean {
//        return try {
//            val ipAddr: InetAddress = InetAddress.getByName("www.google.com")
//            //You can replace it with your name
//            Log.d("TAG", "isInternet: ${ipAddr.address}")
//            !ipAddr.equals("")
//        } catch (e: Exception) {
//            Log.d("TAG", "isInternetAvailable: ${e}")
//            false
//        }
//    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


    override fun onStart() {
        super.onStart()
        if (isNetworkConnected()) {
            userViewModel.getUser()?.observe(this, Observer {
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
            })
        } else {
            Log.d("TAG", "onStart: No Internet Connection")
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPause() {
        super.onPause()
        if (isNetworkConnected()) {
            val userId = userViewModel.getUser()?.value?.id
            if (userId != null) {
                onlineUserViewModel.setMeOffline(userId)
            } else {
                Log.d("TAG", "onPause: Sorry Not Able To Remove From Online")
            }
        }
    }

    override fun onBackPressed() {
        if (flag) {
            AlertDialog.Builder(this)
                .setTitle("Close Game")
                .setMessage("Are you sure you want close the game")
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    super.onBackPressed()
                    flag = false
                }
                .setNegativeButton("No") { _, _ ->
                    flag = true
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        } else {
            super.onBackPressed()
        }
    }
}