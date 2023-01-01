package com.binar.c5team.gotraveladmin.model.booking


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bookings")
    val bookings: List<Booking>
)