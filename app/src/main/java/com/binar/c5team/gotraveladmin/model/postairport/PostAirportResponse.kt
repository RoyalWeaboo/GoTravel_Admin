package com.binar.c5team.gotraveladmin.model.postairport


import com.google.gson.annotations.SerializedName

data class PostAirportResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)