package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class AdminResponse(
    @SerializedName("data")
    val `data`: UserAdminResponseList,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)