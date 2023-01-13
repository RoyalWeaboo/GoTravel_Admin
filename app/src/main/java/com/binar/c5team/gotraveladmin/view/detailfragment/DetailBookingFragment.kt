package com.binar.c5team.gotraveladmin.view.detailfragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailBookingBinding
import com.binar.c5team.gotraveladmin.model.bookingid.Whislists
import com.binar.c5team.gotraveladmin.view.adapter.WhislistAdapter
import com.binar.c5team.gotraveladmin.viewmodel.BookingViewModel
import com.bumptech.glide.Glide

class DetailBookingFragment : Fragment() {

    private lateinit var binding: FragmentDetailBookingBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefUser: SharedPreferences

    private lateinit var adapter: WhislistAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("databooking", Context.MODE_PRIVATE)
        sharedPrefUser = requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)



        binding.tvNamaAkun.text = sharedPref.getString("namaAkun", "")
        binding.tvBagasi.text = "${sharedPref.getInt("baggage", 0)} Kg"
        binding.tvTotalprice.text = "Rp. ${sharedPref.getInt("totalPrice", 0)}"

        binding.tvNama.text = sharedPref.getString("name_user", "")
        binding.tvGender.text = sharedPref.getString("gender_user", "")
        binding.tvEmail.text = sharedPref.getString("email_user", "")
        binding.tvNoHp.text = sharedPref.getString("mobilephone_user", "")
        binding.tvKtp.text = sharedPref.getString("ktp_user", "")

        //val confirmation = sharedPref.getString("confirmation", "")

        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        viewModel.getBookingIdListData().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvWhislist.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                Glide
                    .with(requireContext())
                    .load(it.data.confirmation)
                    .centerCrop()
                    .into(binding.imgBuktiPembayaran)

                val filterBooking: MutableList<Whislists> = ArrayList()
                for (i in it.data.user.whislists) {
                    filterBooking.add(i)
                }
                adapter = WhislistAdapter(filterBooking)
                binding.rvWhislist.adapter = adapter

            }
        }
        viewModel.callBookingDetailData(sharedPref.getInt("whislistId", 0))

        val token = sharedPrefUser.getString("token", "").toString()
        val id = sharedPref.getInt("whislistId", 0)
        val userId = sharedPref.getInt("userId", 0)

        binding.btnAccept.setOnClickListener {
            putApproved(true, token, id)
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it == false) {
                    postNotification(token, userId)
                    findNavController().navigate(R.id.action_detailBookingFragment_to_nav_selectBookingFragment)
                }
            }
        }

        binding.btnDecline.setOnClickListener {
            putApproved(false, token, id)
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it == false) {
                    postDeclinedNotification(token, userId)
                    findNavController().navigate(R.id.action_detailBookingFragment_to_nav_selectBookingFragment)
                }
            }
        }

    }

    private fun putApproved(approved: Boolean, token: String, id: Int) {
        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        viewModel.updateBookingIdListData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Approved Booking Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Edit Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.callUpdateBookingData(approved, token, id)
//        RetrofitClient.apiWithToken(token).putBooking(id, ApprovedData(approved))
//            .enqueue(object : Callback<PutBookingIdResponse> {
//                override fun onResponse(
//                    call: Call<PutBookingIdResponse>,
//                    response: Response<PutBookingIdResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(requireActivity(), "Update Success", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<PutBookingIdResponse>, t: Throwable) {
//                    Log.d("Put Booking Data Error", call.toString())
//                }
//
//            })
    }

    private fun postNotification(token : String, id : Int) {
        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        val message = "Your Ticket by id : $id is approved by Admin ! Your Ticket is now active, please board on time. Have a Safe Flight !"
        viewModel.postNotificationApi(token, message, id)
    }

    private fun postDeclinedNotification(token : String, id : Int) {
        val viewModel = ViewModelProvider(this)[BookingViewModel::class.java]
        val message = "Your Ticket by id : $id is declined by Admin ! if you find this wrong, please contact Customer Service."
        viewModel.postNotificationApi(token, message, id)
    }

}