package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AirportList(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String
): Serializable