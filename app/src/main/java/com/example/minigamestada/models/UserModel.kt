package com.example.minigamestada.models

import android.provider.ContactsContract

data
class UserModel(
    val id: String?,
    val name: String?,
    val email: String?,
    val token: String?,
    val gender: String?,
    val profile_pic: String?,
) {
}