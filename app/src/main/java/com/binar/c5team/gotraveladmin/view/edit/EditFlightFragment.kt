package com.binar.c5team.gotraveladmin.view.edit

import android.app.DatePickerDialog
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
import com.binar.c5team.gotraveladmin.viewmodel.FlightViewModel
import java.text.SimpleDateFormat
import java.util.*


class EditFlightFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentAddFlightBinding
    private lateinit var sharedPrefFlight: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = this.requireActivity().getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        sharedPrefFlight = requireActivity().getSharedPreferences("dataflight", Context.MODE_PRIVATE)

        binding.btnAdd.text = "Edit"

        binding.btnDatePick.setOnClickListener {
            openDatePicker()
        }

        binding.edtId.text = sharedPrefFlight.getInt("id",0).toString()
        binding.edtArrivalTime.setText(sharedPrefFlight.getString("arrival_time","").toString())
        binding.edtSeat.setText(sharedPrefFlight.getInt("available_seats",0).toString())
        binding.edtDepartureTime.setText(sharedPrefFlight.getString("departure_time","").toString())
        binding.edtFlightDate.setText(sharedPrefFlight.getString("flight_date","").toString())
        binding.edtFromAirportId.setText(sharedPrefFlight.getInt("from_airport_id",0).toString())
        binding.edtIdPlane.setText(sharedPrefFlight.getInt("id_plane",0).toString())
        binding.edtClass.setText(sharedPrefFlight.getString("kelas","").toString())
        binding.edtPrice.setText(sharedPrefFlight.getInt("price",0).toString())
        binding.edtToAirportId.setText(sharedPrefFlight.getInt("to_airport_id",0).toString())


        binding.btnAdd.setOnClickListener {
            val token = sharedPref.getString("token","").toString()
            val arrival_time = binding.edtArrivalTime.text.toString()
            val available_seats = binding.edtSeat.text.toString().toInt()
            val departure_time = binding.edtDepartureTime.text.toString()
            val flight_date = binding.edtFlightDate.text.toString()
            val from_airport_id = binding.edtFromAirportId.text.toString().toInt()
            val id = sharedPrefFlight.getInt("id",0)
            val id_plane = binding.edtIdPlane.text.toString().toInt()
            val kelas = binding.edtClass.text.toString()
            val price = binding.edtPrice.text.toString().toInt()
            val to_airport_id = binding.edtToAirportId.text.toString().toInt()

            editFlight(token,id, arrival_time, available_seats, departure_time, flight_date, from_airport_id, id_plane, kelas, price, to_airport_id)
            findNavController().navigate(R.id.action_editFlightFragment_to_nav_flight)
        }
    }

    private fun editFlight(token: String,
                           id: Int,
                           arrival_time: String,
                           available_seats: Int,
                           departure_time: String,
                           flight_date: String,
                           from_airport_id: Int,
                           id_plane: Int,
                           kelas: String,
                           price: Int,
                           to_airport_id: Int) {
        val viewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        viewModel.putFlightData().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Edit Flight Response :", it.toString())
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Edit Failed !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.callPutFlightData(token, id, arrival_time, available_seats, departure_time, flight_date, from_airport_id, id_plane, kelas, price, to_airport_id)

    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, y, m, d ->
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = Calendar.getInstance()
                date.set(y, m, d)
                val dateString = formatter.format(date.time)
                binding.edtFlightDate.setText(dateString)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()
    }
}