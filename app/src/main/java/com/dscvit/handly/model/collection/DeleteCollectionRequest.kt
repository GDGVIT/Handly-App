package com.dscvit.handly.model.collection


import com.google.gson.annotations.SerializedName

data class DeleteCollectionRequest(
    @SerializedName("id")
    val id: String
)