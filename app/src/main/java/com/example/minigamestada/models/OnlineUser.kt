package com.example.minigamestada.models

data class OnlineUser(
    val id: String?,
    var name: String?,
    val subTitle: String?,
    val imgUrl: String?,
    val token: String?,
) {
    constructor():this(null,null,null,null,null)
}