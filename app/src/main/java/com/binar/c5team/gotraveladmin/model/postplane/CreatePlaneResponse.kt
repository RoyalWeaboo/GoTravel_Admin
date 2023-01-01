package com.binar.c5team.gotraveladmin.model.postplane


import com.google.gson.annotations.SerializedName

data class CreatePlaneResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)