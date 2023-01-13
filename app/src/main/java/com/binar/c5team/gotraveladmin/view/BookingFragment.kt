package com.binar.c5team.gotraveladmin.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentBookingBinding
import com.binar.c5team.gotraveladmin.model.booking.Booking
import com.binar.c5team.gotraveladmin.view.adapter.BookingAdapter
import com.binar.c5team.gotraveladmin.viewmodel.BookingViewModel


class BookingFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var adapter: BookingAdapter

    private lateinit var sharedPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("databooking", Context.MODE_PRIVATE)
        showBookingData()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun showBookingData() {
        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        viewModel.getBookingListData().observe(viewLifecycleOwner) {
            binding.rvListBooking.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            val filterBooking: MutableList<Booking> = ArrayList()
            for (i in it.data.bookings) {
                if (i.tripType == "One Way") {
                    filterBooking.add(i)
                }
            }

            adapter = BookingAdapter(filterBooking)
            binding.rvListBooking.adapter = adapter
            binding.rvListBooking.setHasFixedSize(true)
            adapter.notifyDataSetChanged()


            adapter.onCardClick = {its ->
                val bookingInfo = sharedPref.edit()
                bookingInfo.putString("confirmation", its.confirmation)
                bookingInfo.putInt("userId", its.user.id)
                bookingInfo.putInt("whislistId", its.id)
                bookingInfo.putString("namaAkun", its.name)
                bookingInfo.putBoolean("food", its.food)
                bookingInfo.putInt("baggage", its.baggage)
                bookingInfo.putInt("totalPrice", its.totalprice)
                bookingInfo.putString("name_user", its.user.name)
                bookingInfo.putString("gender_user", its.user.gender)
                bookingInfo.putString("email_user", its.user.email)
                bookingInfo.putString("mobilephone_user", its.mobilephone)
                bookingInfo.putString("ktp_user", its.user.noKtp)

                bookingInfo.apply()

                findNavController().navigate(R.id.action_nav_selectBookingFragment_to_detailBookingFragment)
            }
        }
        viewModel.callBookingData()
    }


}