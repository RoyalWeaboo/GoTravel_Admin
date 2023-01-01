package com.binar.c5team.gotraveladmin.model.data

data class CreateAdminData(
    val name: String,
    val username: String,
    val gender: String,
    val date_of_birth: String,
    val no_ktp: String,
    val address: String,
    val email: String,
    val password: String,
    val role: String
)
