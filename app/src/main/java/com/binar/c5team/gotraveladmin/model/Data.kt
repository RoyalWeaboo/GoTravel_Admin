package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("planes")
    val planes: List<PlaneList>
)