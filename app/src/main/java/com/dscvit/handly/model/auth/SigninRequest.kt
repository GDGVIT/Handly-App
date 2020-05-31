package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class SigninRequest(
    val password: String,
    @SerializedName("player_id")
    val playerId: String,
    val username: String
)