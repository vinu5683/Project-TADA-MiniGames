package com.example.minigamestada

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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