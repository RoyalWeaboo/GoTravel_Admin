package com.binar.c5team.gotraveladmin

import com.binar.c5team.gotraveladmin.model.DataXXXXXXXX
import com.google.gson.annotations.SerializedName

data class NotificationPostResponse(
    @SerializedName("data")
    val `data`: DataXXXXXXXX,
    @SerializedName("status")
    val status: String
)