package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.LoginData
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import com.binar.c5team.gotraveladmin.view.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    var loading = MutableLiveData<Boolean>()
    var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()

    fun postLoginApi(activity: LoginActivity, username: String, password: String) {
        loading.value = true
        RetrofitClient.apiInstance.getLoginData(LoginData(username, password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        loginLiveData.value = response.body()
                        activity.uId = response.body()!!.id
                        activity.usernameRes = response.body()!!.username
                        activity.token = response.body()!!.token
                        loading.value = false
                    } else {
                        Log.d("login response", response.body().toString())
                        loading.value = false
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Login Data Error", call.toString())
                    loading.value = false
                }
            })
    }

}