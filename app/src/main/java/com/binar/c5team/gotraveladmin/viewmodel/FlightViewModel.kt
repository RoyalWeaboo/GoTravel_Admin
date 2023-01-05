package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.data.CreateFlightData
import com.binar.c5team.gotraveladmin.model.data.EditFlightData
import com.binar.c5team.gotraveladmin.model.flight.Flight
import com.binar.c5team.gotraveladmin.model.flight.FlightResponse
import com.binar.c5team.gotraveladmin.model.postflight.CreateFlightResponse
import com.binar.c5team.gotraveladmin.model.putflight.EditFlightResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightViewModel : ViewModel() {
    var loading = MutableLiveData<Boolean>()

    var createFlightLiveData: MutableLiveData<CreateFlightResponse> = MutableLiveData()
    var putFlightLiveData: MutableLiveData<EditFlightResponse> = MutableLiveData()
    var deleteFlightLiveData : MutableLiveData<EditFlightResponse> = MutableLiveData()

    fun deleteFlightData() : MutableLiveData<EditFlightResponse> {
        return deleteFlightLiveData
    }
    
    fun createFlightData(): MutableLiveData<CreateFlightResponse> {
        return createFlightLiveData
    }

    fun putFlightData(): MutableLiveData<EditFlightResponse> {
        return putFlightLiveData
    }

    fun callDeleteFlightData(token: String,id: Int) {
        RetrofitClient.apiWithToken(token).deleteFlight(id)
            .enqueue(object : Callback<EditFlightResponse> {
                override fun onResponse(
                    call: Call<EditFlightResponse>,
                    response: Response<EditFlightResponse>
                ) {
                    if (response.isSuccessful) {
                        deleteFlightLiveData.postValue(response.body())
                    } else {
                        Log.d("delete fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<EditFlightResponse>, t: Throwable) {
                    Log.d("delete failure", call.toString())

                }

            })
    }

    fun callPutFlightData(
        token: String,
        id: Int,
        arrival_time: String,
        available_seats: Int,
        departure_time: String,
        flight_date: String,
        from_airport_id: Int,
        id_plane: Int,
        kelas: String,
        price: Int,
        to_airport_id: Int,
    ) {
        RetrofitClient.apiWithToken(token).putFlight(id, EditFlightData(arrival_time, available_seats, departure_time, flight_date, from_airport_id, id_plane, kelas, price, to_airport_id))
            .enqueue(object : Callback<EditFlightResponse> {
                override fun onResponse(
                    call: Call<EditFlightResponse>,
                    response: Response<EditFlightResponse>
                ) {
                    if (response.isSuccessful) {
                        putFlightLiveData.postValue(response.body())
                    } else {
                        Log.d("update fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<EditFlightResponse>, t: Throwable) {
                    Log.d("update failure", call.toString())

                }

            })
    }

    fun postFlightData(
        token: String,
        arrival_time: String,
        available_seats: Int,
        departure_time: String,
        flight_date: String,
        from_airport_id: Int,
        id: Int,
        id_plane: Int,
        kelas: String,
        price: Int,
        to_airport_id: Int,
    ) {
        loading.postValue(true)
        RetrofitClient.apiWithToken(token).createFlight(
            CreateFlightData(
                arrival_time,
                available_seats,
                departure_time,
                flight_date,
                from_airport_id,
                id,
                id_plane,
                kelas,
                price,
                to_airport_id
            )
        )
            .enqueue(object : Callback<CreateFlightResponse> {
                override fun onResponse(
                    call: Call<CreateFlightResponse>,
                    response: Response<CreateFlightResponse>
                ) {
                    if (response.isSuccessful) {
                        createFlightLiveData.postValue(response.body())
                    } else {
                        Log.d("Create Failed", response.body().toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<CreateFlightResponse>, t: Throwable) {
                    Log.d("Create Error", call.toString())
                    loading.postValue(false)
                }

            })
    }

    fun callFlightData(callback: (List<Flight>) -> Unit) {
        RetrofitClient.apiInstance.getFlight()
            .enqueue(object : Callback<FlightResponse> {
                override fun onResponse(
                    call: Call<FlightResponse>,
                    response: Response<FlightResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Fetch Profile Data Success", response.body().toString())
                        return callback(response.body()!!.data.flights)
                    } else {
                        Log.d("Fetch Profile Data Error", call.toString())
                    }
                }

                override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                    Log.d("Fetch Profile Data Error", call.toString())
                }

            })
    }
}