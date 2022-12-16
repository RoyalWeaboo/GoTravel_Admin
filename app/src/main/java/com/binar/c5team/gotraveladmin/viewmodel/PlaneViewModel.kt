package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.PlaneList
import com.binar.c5team.gotraveladmin.model.PlaneResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaneViewModel: ViewModel() {
    fun callPlaneData(callback : (List<PlaneList>) -> Unit) {
        RetrofitClient.apiInstance.getPlane()
            .enqueue(object : Callback<PlaneResponse> {
                override fun onResponse(
                    call: Call<PlaneResponse>,
                    response: Response<PlaneResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Fetch Profile Data Success", response.body().toString())
                        return callback(response.body()!!.data.planes)
                    } else {
                        Log.d("Fetch Profile Data Failed", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<PlaneResponse>, t: Throwable) {
                    Log.d("Fetch Profile Data Error", call.toString())
                }
            })
    }
}