package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class PlaneResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)