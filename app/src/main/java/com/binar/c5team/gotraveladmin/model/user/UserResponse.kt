package com.binar.c5team.gotraveladmin.model.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)