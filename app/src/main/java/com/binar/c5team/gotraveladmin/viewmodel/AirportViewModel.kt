package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.AirportResponse
import com.binar.c5team.gotraveladmin.model.data.CreateAirportData
import com.binar.c5team.gotraveladmin.model.postairport.PostAirportResponse
import com.binar.c5team.gotraveladmin.model.putairport.EditAirportResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportViewModel : ViewModel() {
    var loading = MutableLiveData<Boolean>()

    var createAirportLiveData: MutableLiveData<PostAirportResponse> = MutableLiveData()
    var deleteAirportLiveData: MutableLiveData<EditAirportResponse> = MutableLiveData()
    var putAirportLiveData: MutableLiveData<EditAirportResponse> = MutableLiveData()
    var adminLiveData: MutableLiveData<AirportResponse> = MutableLiveData()

    fun getAirportData(): MutableLiveData<AirportResponse> {
        return adminLiveData
    }

    fun createAirportData(): MutableLiveData<PostAirportResponse> {
        return createAirportLiveData
    }

    fun deleteAirportData(): MutableLiveData<EditAirportResponse> {
        return deleteAirportLiveData
    }

    fun putAirportData(): MutableLiveData<EditAirportResponse> {
        return deleteAirportLiveData
    }

    fun callAirportData() {
        loading.postValue(true)
        RetrofitClient.apiInstance.getAirport()
            .enqueue(object : Callback<AirportResponse> {
                override fun onResponse(
                    call: Call<AirportResponse>,
                    response: Response<AirportResponse>
                ) {
                    if (response.isSuccessful) {
                        adminLiveData.postValue(response.body())
                        Log.d("Fetch Airport Data Success", response.body().toString())
                        loading.postValue(false)
                    } else {
                        Log.d("Fetch Airport Data Failed", response.body().toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                    Log.d("Fetch Airport Data Error", call.toString())
                    loading.postValue(false)
                }
            })
    }

    fun callPutAirportData(
        token: String,
        id: Int,
        city: String,
        code: String,
        country: String,
        name: String,
        province: String,
        status: String
    ) {
        RetrofitClient.apiWithToken(token)
            .putAirport(id, CreateAirportData(city, code, country, name, province, status))
            .enqueue(object : Callback<EditAirportResponse> {
                override fun onResponse(
                    call: Call<EditAirportResponse>,
                    response: Response<EditAirportResponse>
                ) {
                    if (response.isSuccessful) {
                        putAirportLiveData.postValue(response.body())
                    } else {
                        Log.d("update fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<EditAirportResponse>, t: Throwable) {
                    Log.d("update failure", call.toString())

                }

            })
    }

    fun callDeleteAirportData(token: String, id: Int) {
        RetrofitClient.apiWithToken(token).deleteAirport(id)
            .enqueue(object : Callback<EditAirportResponse> {
                override fun onResponse(
                    call: Call<EditAirportResponse>,
                    response: Response<EditAirportResponse>
                ) {
                    if (response.isSuccessful) {
                        deleteAirportLiveData.postValue(response.body())
                    } else {
                        Log.d("delete fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<EditAirportResponse>, t: Throwable) {
                    Log.d("delete failure", call.toString())

                }

            })
    }

    fun postAirportData(
        token: String,
        city: String,
        code: String,
        country: String,
        name: String,
        province: String,
        status: String
    ) {
        loading.postValue(true)
        RetrofitClient.apiWithToken(token)
            .createAirport(CreateAirportData(city, code, country, name, province, status))
            .enqueue(object : Callback<PostAirportResponse> {
                override fun onResponse(
                    call: Call<PostAirportResponse>,
                    response: Response<PostAirportResponse>
                ) {
                    if (response.isSuccessful) {
                        createAirportLiveData.postValue(response.body())
                        loading.postValue(false)
                    } else {
                        Log.d("Create Failed", response.body().toString())
                        loading.postValue(false)
                    }
                }

                override fun onFailure(call: Call<PostAirportResponse>, t: Throwable) {
                    Log.d("Create Error", call.toString())
                    loading.postValue(false)
                }

            })
    }

//    fun callAirportData(callback: (List<AirportList>) -> Unit) {
//        RetrofitClient.apiInstance.getAirport()
//            .enqueue(object : Callback<AirportResponse> {
//                override fun onResponse(
//                    call: Call<AirportResponse>,
//                    response: Response<AirportResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        Log.d("Fetch Profile Data Success", response.body().toString())
//                        return callback(response.body()!!.data.airports)
//                    } else {
//                        Log.d("Fetch Profile Data Error", call.toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
//                    Log.d("Fetch Profile Data Error", call.toString())
//                }
//            })
//    }
}