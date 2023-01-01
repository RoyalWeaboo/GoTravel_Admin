package com.binar.c5team.gotraveladmin.model.flight


import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)