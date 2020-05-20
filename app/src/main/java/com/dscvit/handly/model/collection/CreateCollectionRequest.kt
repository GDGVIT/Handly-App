package com.dscvit.handly.model.collection


import com.google.gson.annotations.SerializedName

data class CreateCollectionRequest(
    @SerializedName("name")
    val name: String
)