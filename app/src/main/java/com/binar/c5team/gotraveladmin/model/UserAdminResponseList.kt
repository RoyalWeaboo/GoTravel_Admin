package com.binar.c5team.gotraveladmin.model


import com.google.gson.annotations.SerializedName

data class UserAdminResponseList(
    @SerializedName("users")
    val user: List<UserAdminResponse>
)