package com.binar.c5team.gotraveladmin.network

import com.binar.c5team.gotraveladmin.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestfulApi {
    @POST("login")
    fun getLoginData(@Body login : LoginData) : Call<LoginResponse>

    @GET("admin")
    fun getAdmin() : Call<AdminResponse>

    @GET("plane")
    fun getPlane() : Call<PlaneResponse>
}