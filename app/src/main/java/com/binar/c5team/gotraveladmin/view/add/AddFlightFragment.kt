package com.binar.c5team.gotraveladmin.view.add

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
import com.binar.c5team.gotraveladmin.R
import com.binar.c5team.gotraveladmin.databinding.FragmentAddFlightBinding
import com.binar.c5team.gotraveladmin.databinding.FragmentAddPlaneBinding
import com.binar.c5team.gotraveladmin.viewmodel.FlightViewModel
import com.binar.c5team.gotraveladmin.viewmodel.PlaneViewModel

class AddFlightFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddFlightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        binding.btnAdd.setOnClickListener {
            val viewModel = ViewModelProvider(this)[FlightViewModel::class.java]
            val token = sharedPref.getString("token", "").toString()
            val arrival_time = binding.edtArrivalTime.text.toString()
            val available_seats = binding.edtSeat.text.toString()
            val departure_time = binding.edtDepartureTime.text.toString()
            val flight_date = binding.edtFlightDate.text.toString()
            val from_airport_id = binding.edtFromAirportId.text.toString()
            val id = binding.edtId.text.toString()
            val id_plane = binding.edtIdPlane.text.toString()
            val kelas = binding.edtClass.text.toString()
            val price = binding.edtPrice.text.toString()
            val to_airport_id = binding.edtToAirportId.text.toString()

            createFlight(
                token,
                arrival_time,
                available_seats.toInt(),
                departure_time,
                flight_date,
                from_airport_id.toInt(),
                id.toInt(),
                id_plane.toInt(),
                kelas,
                price.toInt(),
                to_airport_id.toInt()
            )
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it == false) {
                    findNavController().navigate(R.id.action_addFlightFragment_to_nav_flight)
                }
            }
        }
    }

    private fun createFlight(
        token: String,
        arrival_time: String,
        available_seats: Int,
        departure_time: String,
        flight_date: String,
        from_airport_id: Int,
        id: Int,
        id_plane: Int,
        kelas: String,
        price: Int,
        to_airport_id: Int,
    ) {
        val viewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        viewModel.createFlightData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Create Flight Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Create Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.postFlightData(
            token,
            arrival_time,
            available_seats,
            departure_time,
            flight_date,
            from_airport_id,
            id,
            id_plane,
            kelas,
            price,
            to_airport_id
        )

    }
}