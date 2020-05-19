package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class SigninRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("player_id")
    val playerId: String,
    @SerializedName("username")
    val username: String
)