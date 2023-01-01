package com.binar.c5team.gotraveladmin.model.postflight


import com.google.gson.annotations.SerializedName

data class CreateFlightResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)