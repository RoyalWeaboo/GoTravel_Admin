package com.binar.c5team.gotraveladmin.model.createadmin


import com.google.gson.annotations.SerializedName

data class CreateAdminResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)