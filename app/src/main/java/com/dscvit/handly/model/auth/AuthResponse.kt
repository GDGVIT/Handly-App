package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: Payload
)