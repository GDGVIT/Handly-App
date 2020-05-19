package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("onesig_id")
    val onesigId: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String
)