package com.example.minigamestada.recyclerviews.onlineplayers

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minigamestada.R
import com.example.minigamestada.models.OnlineUser
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

class OnlinePlayersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardView: MaterialCardView
    val userProfileImg: ImageView
    val userName: TextView
    val userSubTitle: TextView
    val addToFriendList: MaterialButton
    val requestGameAction: MaterialButton
    var userId: String

    init {
        cardView = itemView.findViewById(R.id.cardItem)
        userProfileImg = itemView.findViewById(R.id.userProfileImg)
        userName = itemView.findViewById(R.id.userName)
        userSubTitle = itemView.findViewById(R.id.userSubTitle)
        addToFriendList = itemView.findViewById(R.id.addToFriendListAction)
        requestGameAction = itemView.findViewById(R.id.requestFriendAction)
        userId = "N/A"
    }

    fun setData(onlineuser: OnlineUser) {
        try {
            userId = onlineuser.id.toString()
            if (onlineuser.imgUrl != null && onlineuser.imgUrl != "N/A" && onlineuser.imgUrl != "")
                Glide.with(itemView.context).load(onlineuser.imgUrl).into(userProfileImg)
            else
                Glide.with(itemView.context).load(R.drawable.ic_noun_user_temp).into(userProfileImg)
            userName.text = onlineuser.name
            userSubTitle.text = onlineuser.subTitle

            actionListeners(onlineuser)
            Log.d("TAG", "setData: " + userId)
        } catch (e: Exception) {
            println("Some error" + e.localizedMessage)
        }

    }

    private fun actionListeners(onlineuser: OnlineUser) {
        addToFriendList.setOnClickListener {

        }
        requestGameAction.setOnClickListener {

        }
    }


}