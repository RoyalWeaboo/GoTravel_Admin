package com.binar.c5team.gotraveladmin.model.flight


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)