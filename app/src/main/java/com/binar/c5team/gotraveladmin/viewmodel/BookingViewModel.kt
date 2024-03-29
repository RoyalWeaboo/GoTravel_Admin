package com.binar.c5team.gotraveladmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.c5team.gotraveladmin.NotificationPostResponse
import com.binar.c5team.gotraveladmin.model.booking.BookingResponse
import com.binar.c5team.gotraveladmin.model.bookingid.ApprovedData
import com.binar.c5team.gotraveladmin.model.bookingid.BookingResponseId
import com.binar.c5team.gotraveladmin.model.data.NotificationData
import com.binar.c5team.gotraveladmin.model.putbooking.PutBookingIdResponse
import com.binar.c5team.gotraveladmin.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingViewModel: ViewModel() {

    var loading = MutableLiveData<Boolean>()

    var bookingIdLiveData : MutableLiveData<BookingResponseId> = MutableLiveData()
    var updateBookingLiveData : MutableLiveData<PutBookingIdResponse> = MutableLiveData()
    var bookingLiveData : MutableLiveData<BookingResponse> = MutableLiveData()

    var postNotificationLiveData: MutableLiveData<NotificationPostResponse> = MutableLiveData()

    fun getBookingIdListData(): MutableLiveData<BookingResponseId> {
        return bookingIdLiveData
    }

    fun getBookingListData(): MutableLiveData<BookingResponse> {
        return bookingLiveData
    }

    fun updateBookingIdListData(): MutableLiveData<PutBookingIdResponse> {
        return updateBookingLiveData
    }

    fun callUpdateBookingData(approved: Boolean,token: String,id: Int) {
        loading.postValue(true)
        RetrofitClient.apiWithToken(token).putBooking(id, ApprovedData(approved))
            .enqueue(object : Callback<PutBookingIdResponse> {
                override fun onResponse(
                    call: Call<PutBookingIdResponse>,
                    response: Response<PutBookingIdResponse>
                ) {
                    if (response.isSuccessful) {
                        updateBookingLiveData.postValue(response.body())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<PutBookingIdResponse>, t: Throwable) {
                    Log.d("Put Booking Data Error", call.toString())
                    loading.postValue(false)
                }

            })
    }


    fun callBookingData() {
        RetrofitClient.apiInstance.getBooking()
            .enqueue(object : Callback<BookingResponse> {
                override fun onResponse(
                    call: Call<BookingResponse>,
                    response: Response<BookingResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Fetch Profile Data Success", response.body().toString())
                        bookingLiveData.postValue(response.body())
                    } else {
                        Log.d("Fetch Profile Data Error", call.toString())
                    }
                }
                override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                    Log.d("Fetch Profile Data Error", call.toString())
                }
            })
    }

    fun callBookingDetailData(id: Int) {
        RetrofitClient.apiInstance.getBookingDetail(id)
            .enqueue(object : Callback<BookingResponseId> {
                override fun onResponse(
                    call: Call<BookingResponseId>,
                    response: Response<BookingResponseId>
                ) {
                    if (response.isSuccessful) {
                        bookingIdLiveData.postValue(response.body())
                    } else {
                        Log.d("Fetch Profile Data Error", call.toString())
                    }
                }
                override fun onFailure(call: Call<BookingResponseId>, t: Throwable) {
                    Log.d("Fetch Profile Data Error", call.toString())
                }
            })
    }

    fun postNotificationApi(token: String, message: String, idUser : Int) {
        loading.postValue(true)
        RetrofitClient.apiWithToken(token).postNotification(NotificationData(message, idUser))
            .enqueue(object : Callback<NotificationPostResponse> {
                override fun onResponse(
                    call: Call<NotificationPostResponse>,
                    response: Response<NotificationPostResponse>
                ) {
                    if (response.isSuccessful) {
                        postNotificationLiveData.postValue(response.body())
                        Log.d("Post Notification Success, id is", response.body()!!.data.id.toString())
                        loading.postValue(false)
                    } else {
                        Log.d("Post Notification Failed", response.body().toString())
                        loading.postValue(false)
                    }
                }

                override fun onFailure(call: Call<NotificationPostResponse>, t: Throwable) {
                    Log.d("Post Notification Error", call.toString())
                    loading.postValue(false)
                }

            })
    }
}