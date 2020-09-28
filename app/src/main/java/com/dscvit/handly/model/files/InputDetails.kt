package com.dscvit.handly.model.files


import com.google.gson.annotations.SerializedName

data class InputDetails(
    val collection: String,
    @SerializedName("error_logger")
    val errorLogger: String,
    @SerializedName("error_status")
    val errorStatus: Boolean,
    val id: String,
    @SerializedName("input_file")
    val inputFile: String,
    val name: String,
    val status: Boolean
)