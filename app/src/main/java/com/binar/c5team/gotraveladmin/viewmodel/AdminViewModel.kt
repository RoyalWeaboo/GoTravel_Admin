package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.model.LoginResponse
import com.binar.c5team.gotraveladmin.model.AdminResponse
import com.binar.c5team.gotraveladmin.model.UserAdminResponse
import com.binar.c5team.gotraveladmin.model.admin.AdminResponseNew
import com.binar.c5team.gotraveladmin.model.admin.Data
import com.binar.c5team.gotraveladmin.model.admin.User
import com.binar.c5team.gotraveladmin.model.createadmin.CreateAdminResponse
import com.binar.c5team.gotraveladmin.model.data.CreateAdminData
import com.binar.c5team.gotraveladmin.model.user.UserResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel: ViewModel() {
    var userLiveData: MutableLiveData<UserResponse> = MutableLiveData()

    var loginLiveData : MutableLiveData<LoginResponse> = MutableLiveData()
    var adminLiveData : MutableLiveData<AdminResponseNew> = MutableLiveData()
    var createAdminLiveData : MutableLiveData<CreateAdminResponse> = MutableLiveData()

    fun getAdminData() : MutableLiveData<AdminResponseNew> {
        return adminLiveData
    }

    fun createAdminData() : MutableLiveData<CreateAdminResponse> {
        return createAdminLiveData
    }

    fun getUserData() : MutableLiveData<UserResponse> {
        return userLiveData
    }

    fun callUserData(token: String) {
        RetrofitClient.apiWithToken(token).getUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        userLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("User Data Error", call.toString())

                }

            })
    }

    fun callAdminData(token: String) {
        RetrofitClient.apiWithToken(token).getAdmin()
            .enqueue(object : Callback<AdminResponseNew> {
                override fun onResponse(
                    call: Call<AdminResponseNew>,
                    response: Response<AdminResponseNew>
                ) {
                    if (response.isSuccessful) {
                        adminLiveData.postValue(response.body())
                        Log.d("Fetch Admin Data Success", response.body().toString())
                    } else {
                        Log.d("Fetch Admin Data Failed", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AdminResponseNew>, t: Throwable) {
                    Log.d("Fetch Admin Data Error", call.toString())

                }

            })
    }

    fun callCreateAdmin( name: String,
                     username: String,
                     gender: String,
                     date_of_birth: String,
                     no_ktp: String,
                     address: String,
                     email: String,
                     password: String,
                     role: String,
                     token: String
    ) {

        RetrofitClient.apiWithToken(token).createAdmin(CreateAdminData(name, username, gender, date_of_birth, no_ktp, address, email, password, role))
            .enqueue(object : Callback<CreateAdminResponse> {
                override fun onResponse(
                    call: Call<CreateAdminResponse>,
                    response: Response<CreateAdminResponse>
                ) {
                    if (response.isSuccessful) {
                        createAdminData().postValue(response.body())
                    } else {
                        Log.d("Create Failed", response.body().toString())

                    }
                }

                override fun onFailure(call: Call<CreateAdminResponse>, t: Throwable) {
                    Log.d("Create Error", call.toString())

                }

            })

    }
}