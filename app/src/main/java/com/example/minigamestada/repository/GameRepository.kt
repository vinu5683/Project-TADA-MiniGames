package com.example.minigamestada.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.minigamestada.models.GameHistory
import com.example.minigamestada.models.OnlineUser
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
    var gameHistory = MutableLiveData<ArrayList<GameHistory>>()
    var onlineUsersList = MutableLiveData<ArrayList<OnlineUser>>()

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

    fun addMeToOnline(onlineUser: OnlineUser) {
        if (onlineUser?.id == null) {
            Log.d("TAG", "addMeToOnline: Failure bcz you passed null")
            return
        }
        dbRootReference.getReference("onlineUsers").child(onlineUser.id!!.toString())
            .setValue("online")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG", "addMeToOnline: Success")
                } else {
                    Log.d("TAG", "addMeToOnline: Failure bcz " + it.exception?.localizedMessage)
                }
            }
    }

    fun setMeOffline(id: String) {
        dbRootReference.getReference("onlineUsers").child(id).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("TAG", "RemoveFromOnline: Success")
            } else {
                Log.d("TAG", "RemoveFromOnline: Failure bcz " + it.exception?.localizedMessage)
            }
        }
    }

    fun getGameHistoryByUserId(id: String): MutableLiveData<ArrayList<GameHistory>> {

        dbRootReference.getReference("gamehistory").child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            gameHistory.value?.add(
                                0,
                                GameHistory(
                                    it.child("id").value.toString(),
                                    it.child("opId").value.toString(),
                                    it.child("time").value.toString().toLong(),
                                    it.child("win").value.toString().toBoolean(),
                                    it.child("matrix").value.toString(),
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        return gameHistory
    }

    fun getAllOnlineUsers(): MutableLiveData<java.util.ArrayList<OnlineUser>> {
        dbRootReference.getReference("onlineUsers")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            onlineUsersList.value?.add(
                                0,
                                OnlineUser(
                                    it.child("id").value.toString(),
                                    it.child("name").value.toString(),
                                    it.child("subTitle").value.toString(),
                                    it.child("imgUrl").value.toString(),
                                    it.child("token").value.toString(),
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        return onlineUsersList
    }

    fun addToGameHistory(gameHistory: GameHistory) {
        dbRootReference.getReference("gamehistory").child(gameHistory.id.toString())
            .setValue(gameHistory).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG", "addToGameHistory: Success")
                } else {
                    Log.d("TAG", "addToGameHistory: Failed bcz " + it.exception?.localizedMessage)
                }
            }
    }

}