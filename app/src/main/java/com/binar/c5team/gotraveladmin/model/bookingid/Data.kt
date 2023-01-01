package com.binar.c5team.gotraveladmin.model.bookingid


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("approved")
    val approved: Boolean,
    @SerializedName("baggage")
    val baggage: Int,
    @SerializedName("booking_date")
    val bookingDate: Any,
    @SerializedName("confirmation")
    val confirmation: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Flight")
    val flight: Flight,
    @SerializedName("food")
    val food: Boolean,
    @SerializedName("homephone")
    val homephone: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_flight")
    val idFlight: Int,
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("mobilephone")
    val mobilephone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("Ticket")
    val ticket: Any,
    @SerializedName("totalprice")
    val totalprice: Any,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: User
)