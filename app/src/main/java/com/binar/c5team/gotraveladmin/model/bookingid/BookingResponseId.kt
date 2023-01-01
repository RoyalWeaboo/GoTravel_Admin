package com.binar.c5team.gotraveladmin.model.bookingid


import com.google.gson.annotations.SerializedName

data class BookingResponseId(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)