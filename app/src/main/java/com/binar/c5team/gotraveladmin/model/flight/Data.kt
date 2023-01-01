package com.binar.c5team.gotraveladmin.model.flight


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("flights")
    val flights: List<Flight>
)