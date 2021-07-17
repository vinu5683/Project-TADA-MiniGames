package com.example.minigamestada.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.minigamestada.models.Friend
import com.example.minigamestada.models.GameHistory
import com.example.minigamestada.models.OnlineUser
import com.example.minigamestada.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging

class GameRepository {

    val dbRootReference = FirebaseDatabase.getInstance()
    val userModel = MutableLiveData<UserModel>()
    var isDoneFetching = false
    var gameHistory = MutableLiveData<ArrayList<GameHistory>>()
    var onlineUsersList = MutableLiveData<ArrayList<OnlineUser>>()
    var myFriends = MutableLiveData<ArrayList<Friend>>()
    var keepIds = HashSet<String>()

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
        if (onlineUser.id == null) {
            Log.d("TAG", "addMeToOnline: Failure bcz you passed null")
            return
        }
        val x = dbRootReference.getReference("onlineUsers").child(onlineUser.id.toString())
        x.onDisconnect().removeValue()
        x.setValue(onlineUser)
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
                        gameHistory.value?.clear()
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

    fun getAllOnlineUsers(userId: String?) {

        val childRef = dbRootReference.getReference("onlineUsers")
        childRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    val x: ArrayList<OnlineUser>
//                    Log.d("TAG", "onChildAdded: $previousChildName")
//                    val newUser = snapshot.getValue(OnlineUser::class.java)!!
//                    if (onlineUsersList.value != null) {
//                        if (keepIds.contains(newUser.id)) {
//                            return
//                        } else {
//                            x = onlineUsersList.value!!
//                            x.add(newUser)
//                            onlineUsersList.value = x
//                        }
//                    }
                getOnlineUsers()


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                if (onlineUsersList.value != null) {
                    val x = onlineUsersList.value!!
                    val removedId = snapshot.child("id").value.toString();
                    for (i in 0 until x.size) {
                        if (x[i].id == removedId) {
                            val f = x.remove(x[i])
                            keepIds.remove(removedId)
                            Log.d("TAG", "onChildRemoved: $f")
                            break
                        }
                    }
                    onlineUsersList.value = x
                } else {

                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun getOnlineUsers() {
        dbRootReference.getReference("onlineUsers")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val x = ArrayList<OnlineUser>()
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            keepIds.add(it.child("id").value.toString())
                            Log.d(
                                "TAG", "onDataChange: testing " + it.toString(),
                            )
                            x.add(
                                0,
                                OnlineUser(
                                    it.child("id").value.toString(),
                                    it.child("name").value.toString(),
                                    it.child("subTitle").value.toString(),
                                    it.child("imgUrl").value.toString(),
                                    it.child("token").value.toString(),
                                )
                            )
                            onlineUsersList.value = x
                        }
                    } else {
                        Log.d(
                            "TAG", "onDataChange: " + "not exist",
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(
                        "TAG", "onDataChange: " + "not exist error",
                    )
                }

            })
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

    fun addToFriendsList(myId: String, friendId: String) {
        dbRootReference.getReference("friendslist").child(myId).child(friendId).setValue("friend")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG", "addToFriendsList: success")
                } else {
                    Log.d("TAG", "addToFriendsList: success")
                }
            }
    }

    fun getAllMyFriends(myId: String) {
        dbRootReference.getReference("friendslist").child(myId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        myFriends.value?.clear()
                        snapshot.children.forEach {
                            myFriends.value?.add(
                                Friend(
                                    it.key,
                                    it.child(it.key!!).value.toString()
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: Error fetching friends")
                }

            })
    }

}