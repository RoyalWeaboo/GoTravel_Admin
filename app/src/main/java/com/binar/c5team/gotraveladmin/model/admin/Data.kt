package com.binar.c5team.gotraveladmin.model.admin


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("users")
    val users: List<User>
)