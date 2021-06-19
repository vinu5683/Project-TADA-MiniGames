package com.example.minigamestada.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.minigamestada.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging

class GameRepository {

    val dbRootReference = FirebaseDatabase.getInstance()
    val userModel = MutableLiveData<UserModel>()
    var isDoneFetching = false

    init {

    }

    fun getUser(): MutableLiveData<UserModel>? {
        Log.d("TAG", "onViewCreated: " + userModel.value.toString())
        return userModel
    }

    fun getCurrentUserDetails(userGoogleId: String?): UserModel? {
        checkAndGetUser(userGoogleId)
        while (!isDoneFetching) {
            break
        }
        return userModel.value
    }

    private fun checkAndGetUser(userGoogleId: String?) {

        dbRootReference.getReference("users").child(userGoogleId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        userModel.value = UserModel(
                            snapshot.child("id").value.toString(),
                            snapshot.child("name").value.toString(),
                            snapshot.child("email").value.toString(),
                            snapshot.child("token").value.toString(),
                            snapshot.child("gender").value.toString(),
                            snapshot.child("profile_pic").value.toString(),
                        )
                        isDoneFetching
                    } else {
                        userModel.value = null
                        isDoneFetching = true
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    isDoneFetching = true
                    userModel.value = null
                }

            })
    }

    fun saveUser(account: GoogleSignInAccount) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { it ->
            var userModel: UserModel? = null
            if (it.isSuccessful) {
                userModel = UserModel(
                    id = account.id,
                    name = account.displayName,
                    email = account.email,
                    profile_pic = account.photoUrl.toString(),
                    token = it.result,
                    gender = "N/A"
                )
            } else {
                userModel = UserModel(
                    id = account.id,
                    name = account.displayName,
                    email = account.email,
                    profile_pic = account.photoUrl.toString(),
                    token = "N/A",
                    gender = "N/A"
                )
            }
            dbRootReference.getReference("users").child(account.id.toString())
                .setValue(userModel).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("TAG", "saveUser: Successful")
                    } else {
                        Log.d("TAG", "saveUser: UnSuccessful")
                    }
                }
        }
    }

}