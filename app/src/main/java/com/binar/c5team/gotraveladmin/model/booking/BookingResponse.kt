package com.binar.c5team.gotraveladmin.model.booking


import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)