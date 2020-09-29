package com.dscvit.handly.model.files


import com.google.gson.annotations.SerializedName

data class UploadFileResponse(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("error_logger")
    val errorLogger: String,
    @SerializedName("error_status")
    val errorStatus: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Boolean
)