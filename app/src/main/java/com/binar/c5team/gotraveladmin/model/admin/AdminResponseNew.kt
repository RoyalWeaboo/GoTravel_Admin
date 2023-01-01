package com.binar.c5team.gotraveladmin.model.admin


import com.google.gson.annotations.SerializedName

data class AdminResponseNew(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)