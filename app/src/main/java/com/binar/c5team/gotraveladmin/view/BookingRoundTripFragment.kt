package com.binar.c5team.gotraveladmin.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentBookingRoundTripBinding
import com.binar.c5team.gotraveladmin.model.booking.Booking
import com.binar.c5team.gotraveladmin.view.adapter.BookingAdapter
import com.binar.c5team.gotraveladmin.viewmodel.BookingViewModel


class BookingRoundTripFragment : Fragment() {
    private lateinit var binding: FragmentBookingRoundTripBinding
    private lateinit var adapter: BookingAdapter

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("databooking", Context.MODE_PRIVATE)
        showBookingData()
    }

    private fun showBookingData() {
        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        viewModel.getBookingListData().observe(viewLifecycleOwner) {
            binding.rvListBooking.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )

            val filterBooking: MutableList<Booking> = ArrayList()
            for (i in it.data.bookings) {
                if (i.tripType == "Round Trip") {
                    filterBooking.add(i)
                }
            }

            adapter = BookingAdapter(filterBooking)
            binding.rvListBooking.adapter = adapter
            binding.rvListBooking.setHasFixedSize(true)
            adapter.notifyDataSetChanged()


            adapter.onCardClick = {
                val bookingInfo = sharedPref.edit()
                bookingInfo.putInt("whislistId", it.id)
                bookingInfo.putString("namaAkun", it.name)
                bookingInfo.putBoolean("food", it.food)
                bookingInfo.putInt("baggage", it.baggage)
                bookingInfo.putInt("totalPrice", it.totalprice)
                bookingInfo.putString("name_user", it.user.name)
                bookingInfo.putString("gender_user", it.user.gender)
                bookingInfo.putString("email_user", it.user.email)
                bookingInfo.putString("mobilephone_user", it.mobilephone)
                bookingInfo.putString("ktp_user", it.user.noKtp)
                bookingInfo.apply()

                findNavController().navigate(R.id.action_nav_selectBookingFragment_to_detailBookingFragment)
            }
        }
        viewModel.callBookingData()
    }
}