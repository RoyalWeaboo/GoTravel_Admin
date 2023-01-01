package com.binar.c5team.gotraveladmin.model.data

import com.google.gson.annotations.SerializedName

data class CreateFlightData(
    val arrival_time: String,
    val available_seats: Int,
    val departure_time: String,
    val flight_date: String,
    val from_airport_id: Int,
    val id: Int,
    val id_plane: Int,
    val kelas: String,
    val price: Int,
    val to_airport_id: Int,
)
