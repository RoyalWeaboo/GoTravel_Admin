package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("meta")
    val meta: MetaX,
    @SerializedName("status")
    val status: String
)