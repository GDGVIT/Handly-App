package com.dscvit.handly.model.auth


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("full_name")
    val fullName: String,
    val id: Int,
    @SerializedName("onesig_id")
    val onesigId: String,
    val token: String,
    val username: String
)