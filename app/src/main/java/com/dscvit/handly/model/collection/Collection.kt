package com.dscvit.handly.model.collection


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)