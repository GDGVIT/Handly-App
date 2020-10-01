package com.dscvit.handly.model.files


import com.google.gson.annotations.SerializedName

data class FileViewResponse(
    @SerializedName("aws_url")
    val awsUrl: String,
    val id: Int,
    @SerializedName("input_details")
    val inputDetails: InputDetails,
    val url: String
)