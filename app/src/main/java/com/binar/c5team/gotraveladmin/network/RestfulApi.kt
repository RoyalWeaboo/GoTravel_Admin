package com.binar.c5team.gotraveladmin.network

import com.binar.c5team.gotraveladmin.model.*
import com.binar.c5team.gotraveladmin.model.admin.AdminResponseNew
import com.binar.c5team.gotraveladmin.model.booking.BookingResponse
import com.binar.c5team.gotraveladmin.model.bookingid.ApprovedData
import com.binar.c5team.gotraveladmin.model.bookingid.BookingResponseId
import com.binar.c5team.gotraveladmin.model.createadmin.CreateAdminResponse
import com.binar.c5team.gotraveladmin.model.data.*
import com.binar.c5team.gotraveladmin.model.delete.plane.DeletePlaneResponse
import com.binar.c5team.gotraveladmin.model.flight.FlightResponse
import com.binar.c5team.gotraveladmin.model.postairport.PostAirportResponse
import com.binar.c5team.gotraveladmin.model.postflight.CreateFlightResponse
import com.binar.c5team.gotraveladmin.model.postplane.CreatePlaneResponse
import com.binar.c5team.gotraveladmin.model.putairport.EditAirportResponse
import com.binar.c5team.gotraveladmin.model.putbooking.PutBookingIdResponse
import com.binar.c5team.gotraveladmin.model.putflight.EditFlightResponse
import com.binar.c5team.gotraveladmin.model.user.UserResponse
import com.binar.c5team.gotraveladmin.view.edit.EditFlightFragment
import retrofit2.Call
import retrofit2.http.*

interface RestfulApi {
    @POST("login")
    fun getLoginData(@Body login : LoginData) : Call<LoginResponse>

    @POST("createAdmin")
    fun createAdmin(@Body create: CreateAdminData) : Call<CreateAdminResponse>

    @GET("Admin")
    fun getAdmin() : Call<AdminResponseNew>

    @GET("user")
    fun getUser() : Call<UserResponse>

    @GET("plane")
    fun getPlane() : Call<PlaneResponse>

    @GET("airport")
    fun getAirport() : Call<AirportResponse>

    @GET("flight")
    fun getFlight() : Call<FlightResponse>

    @GET("booking")
    fun getBooking() : Call<BookingResponse>

    @PUT("booking/{id}")
    fun putBooking(@Path("id") id : Int, @Body approved: ApprovedData) : Call<PutBookingIdResponse>

    @GET("booking/{id}")
    fun getBookingDetail(@Path("id") id : Int) : Call<BookingResponseId>

    @POST("plane")
    fun createPlane(@Body create: CreatePlaneData): Call<CreatePlaneResponse>

    @DELETE("plane/{id}")
    fun deletePlane(@Path("id") id : Int): Call<DeletePlaneResponse>

    @PUT("plane/{id}")
    fun putPlane(@Path("id") id : Int, @Body edit: CreatePlaneData): Call<DeletePlaneResponse>

    @POST("flight")
    fun createFlight(@Body create: CreateFlightData): Call<CreateFlightResponse>

    @PUT("flight/{id}")
    fun putFlight(@Path("id") id : Int,@Body create: EditFlightData): Call<EditFlightResponse>

    @DELETE("flight/{id}")
    fun deleteFlight(@Path("id") id : Int): Call<EditFlightResponse>

    @POST("airport")
    fun createAirport(@Body create: CreateAirportData): Call<PostAirportResponse>

    @PUT("airport/{id}")
    fun putAirport(@Path("id") id : Int,@Body edit: CreateAirportData): Call<EditAirportResponse>

    @DELETE("airport/{id}")
    fun deleteAirport(@Path("id") id : Int): Call<EditAirportResponse>
}