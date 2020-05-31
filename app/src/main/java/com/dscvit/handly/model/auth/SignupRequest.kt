package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("full_name")
    val fullName: String,
    val password: String,
    @SerializedName("player_id")
    val playerId: String,
    val username: String
)