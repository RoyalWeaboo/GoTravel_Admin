package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.PlaneList
import com.binar.c5team.gotraveladmin.model.PlaneResponse
import com.binar.c5team.gotraveladmin.model.data.CreatePlaneData
import com.binar.c5team.gotraveladmin.model.delete.plane.DeletePlaneResponse
import com.binar.c5team.gotraveladmin.model.postplane.CreatePlaneResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaneViewModel: ViewModel() {
    var loading = MutableLiveData<Boolean>()

    var createPlaneLiveData : MutableLiveData<CreatePlaneResponse> = MutableLiveData()
    var deletePlaneLiveData : MutableLiveData<DeletePlaneResponse> = MutableLiveData()
    var putPlaneLiveData : MutableLiveData<DeletePlaneResponse> = MutableLiveData()


    fun createPlaneData() : MutableLiveData<CreatePlaneResponse> {
        return createPlaneLiveData
    }

    fun deletePlaneData() : MutableLiveData<DeletePlaneResponse> {
        return deletePlaneLiveData
    }

    fun putPlaneData() : MutableLiveData<DeletePlaneResponse> {
        return deletePlaneLiveData
    }

    fun callPutPlaneData(token: String,
                         id: Int,
                         code: Int,
                         name: String,
                         status: String) {
        RetrofitClient.apiWithToken(token).putPlane(id, CreatePlaneData(code, name, status))
            .enqueue(object : Callback<DeletePlaneResponse> {
                override fun onResponse(
                    call: Call<DeletePlaneResponse>,
                    response: Response<DeletePlaneResponse>
                ) {
                    if (response.isSuccessful) {
                        putPlaneLiveData.postValue(response.body())
                    } else {
                        Log.d("update fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<DeletePlaneResponse>, t: Throwable) {
                    Log.d("update failure", call.toString())

                }

            })
    }


    fun callDeletePlaneData(token: String,id: Int) {
        RetrofitClient.apiWithToken(token).deletePlane(id)
            .enqueue(object : Callback<DeletePlaneResponse> {
                override fun onResponse(
                    call: Call<DeletePlaneResponse>,
                    response: Response<DeletePlaneResponse>
                ) {
                    if (response.isSuccessful) {
                        deletePlaneLiveData.postValue(response.body())
                    } else {
                        Log.d("delete fail", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<DeletePlaneResponse>, t: Throwable) {
                    Log.d("delete failure", call.toString())

                }

            })
    }

    fun postPlaneData(token: String ,
                      code: Int,
                      name: String,
                      status: String) {
        loading.postValue(true)
        RetrofitClient.apiWithToken(token).createPlane(CreatePlaneData(code, name, status))
            .enqueue(object : Callback<CreatePlaneResponse> {
                override fun onResponse(
                    call: Call<CreatePlaneResponse>,
                    response: Response<CreatePlaneResponse>
                ) {
                    if (response.isSuccessful) {
                        createPlaneLiveData.postValue(response.body())
                    } else {
                        Log.d("Create Failed", response.body().toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<CreatePlaneResponse>, t: Throwable) {
                    Log.d("Create Error", call.toString())
                    loading.postValue(false)
                }

            })
    }

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