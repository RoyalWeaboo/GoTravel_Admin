package com.binar.c5team.gotraveladmin.view.detailfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailAirportBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailBookingBinding
import com.binar.c5team.gotraveladmin.view.adapter.WhislistAdapter


class DetailAirportFragment : Fragment() {

    private lateinit var binding: FragmentDetailAirportBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailAirportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("dataairport", Context.MODE_PRIVATE)

        if (sharedPref.getString("status","") == "On") {
            binding.imgStatus.setImageResource(R.drawable.status_on)
        } else {
            binding.imgStatus.setImageResource(R.drawable.status_off)
        }

        binding.tvCity.text = sharedPref.getString("city","")
        binding.tvProvince.text = sharedPref.getString("province","")
        binding.tvCountry.text = sharedPref.getString("country","")

    }
}