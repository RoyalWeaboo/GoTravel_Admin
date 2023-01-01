package com.binar.c5team.gotraveladmin.model.data

import com.google.gson.annotations.SerializedName

data class CreateAirportData(
    val city: String,
    val code: String,
    val country: String,
    val name: String,
    val province: String,
    val status: String
)
