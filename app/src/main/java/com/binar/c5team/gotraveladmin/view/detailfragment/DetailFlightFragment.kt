package com.binar.c5team.gotraveladmin.view.detailfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binar.c5team.gotraveladmin.databinding.FragmentDetailFlightBinding

class DetailFlightFragment : Fragment() {

    private lateinit var binding: FragmentDetailFlightBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFlightBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("dataflight", Context.MODE_PRIVATE)

        binding.tvPlaneName.text = sharedPref.getString("planename","")
        binding.tvIdPlane.text = "${sharedPref.getInt("idplane",0)}"
        binding.tvClass.text = sharedPref.getString("class","")
        binding.tvAvailSeat.text = "${sharedPref.getInt("seat", 0)}"
        binding.tvPrice.text = "Rp. ${sharedPref.getInt("price",0)}"
        binding.tvArrivalTime.text = sharedPref.getString("arrival","")
        binding.tvDepartureTime.text = sharedPref.getString("departure","")
        binding.tvFlightDate.text = sharedPref.getString("date","")

    }
}