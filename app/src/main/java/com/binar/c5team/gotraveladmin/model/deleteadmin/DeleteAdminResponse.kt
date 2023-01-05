package com.binar.c5team.gotraveladmin.model.deleteadmin


import com.google.gson.annotations.SerializedName

data class DeleteAdminResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)