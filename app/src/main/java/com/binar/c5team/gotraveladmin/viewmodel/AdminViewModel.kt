package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.model.AdminResponse
import com.binar.c5team.gotraveladmin.model.UserAdminResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel: ViewModel() {

    var loginLiveData : MutableLiveData<LoginResponse> = MutableLiveData()
    var adminLiveData : MutableLiveData<AdminResponse> = MutableLiveData()

    fun getAdminData() : MutableLiveData<AdminResponse> {
        return adminLiveData
    }

    fun callAdminData(token: String, callback : (List<UserAdminResponse>) -> Unit) {
        RetrofitClient.apiWithToken(token).getAdmin()
            .enqueue(object : Callback<AdminResponse> {
                override fun onResponse(
                    call: Call<AdminResponse>,
                    response: Response<AdminResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Fetch Profile Data Success", response.body().toString())
                        return callback(response.body()!!.data.user)
                    } else {
                        Log.d("Fetch Profile Data Failed", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AdminResponse>, t: Throwable) {
                    Log.d("Fetch Profile Data Error", call.toString())

                }

            })
    }
}