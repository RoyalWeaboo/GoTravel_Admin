package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("airports")
    val airports: List<AirportList>
)